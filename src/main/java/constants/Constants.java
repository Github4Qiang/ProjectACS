package constants;

/**
 * Created by sky on 2017/3/15.
 */
public class Constants {

    public static final String KAFKA_QUORUMS_PRODUCER = "192.168.79.101:9092,192.168.79.102:9092,192.168.79.103:9092";
    public static final String KAFKA_KEY_CONFIG = "org.apache.kafka.common.serialization.ByteArraySerializer";
    public static final String KAFKA_VALUE_CONFIG = "org.apache.kafka.common.serialization.ByteArraySerializer";
    public static final String KAFKA_TOPICS = "AdRealTimeLog";
    public static final String KAFKA_CONF_META_LIST = "metadata.broker.list";

    public static final String SPARK_APP_NAME = "ProjectACS";
    public static final int SPARK_BATCH_DURATION = 5;

    public static final String SPARK_CHECKPOINT_DIRECTORY = "hdfs://hdp-node-01:9000/project/acs/checkpoint";

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public static final int CONN_POOL_SIZE = 10;
    public static final Boolean SPARK_LOCAL = true;
    //用于本地测试数据库,url为数据库名字
    public static final String JDBC_URL = "jdbc:MYSQL://192.168.2.32:3306/idcproject";
    public static final String JDBC_USER = "root";
    public static final String JDBC_PASSWORD = "hadoop";
    //用于网络服务器数据库
    public static final String JDBC_URL_PROD = "";
    public static final String JDBC_USER_PROD = "";
    public static final String JDBC_PASSWORD_PROD = "";

    public static final int THREAD_POOL_SIZE = 20;

    public static final int BLACK_LIST_THRESHOLD = 5;
}
