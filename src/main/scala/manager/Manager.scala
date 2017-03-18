package manager

import java.util.Date

import bean._
import constants.Constants
import dao._
import kafka.serializer.StringDecoder
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import utils.{DateTimeUtils, StreamingExamples}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by sky on 2017/3/15.
  */
object Manager {

    def main(args: Array[String]): Unit = {
        // 设置Log级别 -> Warnning
        StreamingExamples.setStreamingLogLevels()

        val conf = new SparkConf().setAppName(Constants.SPARK_APP_NAME)
        val sc = new SparkContext(conf)
        val ssc = new StreamingContext(sc, Seconds(Constants.SPARK_BATCH_DURATION))
        // 设置 Checkpoint Directory
        ssc.checkpoint(Constants.SPARK_CHECKPOINT_DIRECTORY)

        val topicsSet = Set(Constants.KAFKA_TOPICS)
        val kafkaParams = Map[String, String](Constants.KAFKA_CONF_META_LIST -> Constants.KAFKA_QUORUMS_PRODUCER)

        //===============================将原始数据，按照分隔符“\t”切分======================================//
        // (time: Timestamp, provinec: String, city: String, userId: Int, adId: Int)
        val lines = KafkaUtils
                .createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
        val words = lines.map(tuple => tuple._2)
                .map(tuple => tuple.split("\t"))
                .map(word => (word(0).trim.toLong, word(1), word(2), word(3).trim.toInt, word(4).trim.toInt))

        //====================================将原始记录存入数据库============================================//
        // (time: Timestamp, city: String, userId: Int, adId: Int, count: Long)
        words.foreachRDD(rdd =>
            rdd.foreachPartition(partitionOfEntries => {
                partitionOfEntries.foreach(entry =>
                    RecordDAO.insert(new RecordEntry(entry._1, entry._3, entry._4, entry._5, 1L))
                )
            }))

        //===================================从黑名单中读取列表，并且过滤====================================//
        // cleanedStream(time: Timestamp, provinec: String, city: String, userId: Int, adId: Int)
        // => ListEntry(date: Date, userId, adId)
        val cleanedStream = words.filter(entry => {
            val listEntry = new ListEntry(entry._1, entry._4, entry._5)
            !new ListDAO().existInBlackList(listEntry)
        }).cache()

        //=====================计算每个用户，每条广告当天的累计点击量，将非法用户加入到黑名单中=================//
        // 以 (Date, userId, adId) 为 key, 进行累加
        val userClickStream = cleanedStream.map(word => ((DateTimeUtils.roundDate(word._1), word._4, word._5), 1L))
                .reduceByKey(_ + _)
                // 当用户上一次状态值（广告点击量） > 黑名单的阈值
                // 说明用户在上一次就被加入到黑名单中
                // 则在累加中删除该记录（updateStateByKey 返回 None）
                .updateStateByKey((values: Seq[Long], state: Option[Long]) => {
            if (state.getOrElse(0L) > Constants.BLACK_LIST_THRESHOLD) None
            else Some(values.sum + state.getOrElse(0L))
        })

        // 过滤得到黑名单列表，并加入数据库blacklist表中
        val blackListStream = userClickStream.filter(kv => kv._2 > Constants.BLACK_LIST_THRESHOLD)
                .foreachRDD(rdd => rdd.foreachPartition(partitionOfEntries => {
                    partitionOfEntries.foreach(entry =>
                        ListDAO.insert(new ListEntry(entry._1._1, entry._1._2, entry._1._3)))
                }))

        //==============================计算每个城市，每种广告当天的累计点击量=============================//
        // cleanedStream(time: Timestamp, provinec: String, city: String, userId: Int, adId: Int)
        // cityClickStream(date: Date, city: String, adId: Int, count: Long)
        val cityClickStream = cleanedStream.map(word =>
            ((DateTimeUtils.roundDate(word._1), word._3, word._5), 1L))
                .reduceByKey(_ + _)
                .updateStateByKey((values: Seq[Long], state: Option[Long]) => {
                    Some(values.sum + state.getOrElse(0L))
                })

        // 将市数据存入数据库中
        cityClickStream.foreachRDD(rdd =>
            rdd.foreachPartition(partitionOfEntries =>
                partitionOfEntries.foreach(entry =>
                    ResultDAO.insert(new ResultEntry(entry._1._1, entry._1._2, entry._1._3, entry._2)))
            ))

        //============================累加计算各省的每个广告点击量=========================================//
        // cleanedStream(time: Timestamp, provinec: String, city: String, userId: Int, adId: Int)
        // sortedPerProv(date: Date, province: String, adId: Int, count: Long)
        val sortedPerProv = cleanedStream.map(entry =>
            ((DateTimeUtils.roundDate(entry._1), entry._2, entry._5), 1L))
                .reduceByKey(_ + _)
                .updateStateByKey((values: Seq[Long], state: Option[Long]) => {
                    Some(values.sum + state.getOrElse(0L))
                }).transform(_.sortBy(_._2, false).groupBy(_._1._2))

        //取前三个保存到数据库
        sortedPerProv.foreachRDD(rdd => {
            rdd.collect.foreach(records => {
                val top3 = records._2.take(3)
                for (record <- top3) {
                    Top3DAO.insert(new Top3Entry(record._1._2, record._1._3, new Date(record._1._1), record._2))
                }
            })
        })

        //=================================计算每分钟内各广告的点击量====================================//
        // cleanedStream(time: Timestamp, provinec: String, city: String, userId: Int, adId: Int)
        // 宽度为 1min 的窗口，每 1min 滑一次，每次 1min
        // 取得每个广告，每分钟内的点击次数
        val adClickCntPerMin = cleanedStream.map(x => ((DateTimeUtils.roundMinute(x._1), x._5), 1L))
                .reduceByKeyAndWindow((x: Long, y: Long) => x + y, Minutes(1), Seconds(5))

        // 将(每个广告，每分钟内的点击次数)加入数据库中
        adClickCntPerMin.foreachRDD(rdd =>
            rdd.foreachPartition(partitionOfEntries =>
                partitionOfEntries.foreach(entry =>
                    MintDAO.insert(new MintEntry(entry._1._1, entry._1._2, entry._2)))))

        ssc.start()
        ssc.awaitTermination()

    }

}
