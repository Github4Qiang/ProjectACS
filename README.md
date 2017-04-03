# ProjectACS
Spark Streaming

## 1. 模拟产生实时广告点击数据流: Kafka
product/MockRealTimeData.java
> Item(time: Timestamp, provinec: String, city: String, userId: Int, adId: Int)

## 2. 接收实时数据流，并处理：Spark Streaming
### 对原始数据进行切分，并存入数据库中
### 从数据库的黑名单中读取列表，并且对数据流进行过滤
### 累计计算每个用户，每条广告当天的点击量，将非法用户加入到黑名单中（当天对某一条广告点击数 >100 的用户
### 计算每个城市，每种广告当天的累计点击量，并保存（更新）数据库
### 累加计算各省的每个广告点击量，将各省前三甲保存（更新）到数据库中
### 统计每分钟内各个广告的点击量，得到各广告在最近一个小时内的点击趋势
