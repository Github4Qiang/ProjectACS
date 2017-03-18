package utils

import java.util.Date

/**
  * Created by sky on 2017/3/16.
  */
object DateTimeUtils {

    // 从8:00开始为第一个时间
    def roundDate(date: Long): Long = {
        date - date % (1000 * 60 * 60 * 24) - 1000 * 60 * 60 * 8
    }

    def roundMinute(date: Long): Long = {
        date - date % (1000 * 60)
    }

    def tomorrow(date: Long): Long = {
        date + 1000 * 60 * 60 * 24
    }

    def getTodayRoundTime(): Long = {
        roundDate(new Date().getTime())
    }

}
