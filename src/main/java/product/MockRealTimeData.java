package product;

import constants.Constants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

/**
 * Created by sky on 2017/3/15.
 */
public class MockRealTimeData extends Thread {

    private static final Random random = new Random();
    private static final String[] provinces = new String[]{"Sichuan", "Hubei", "Hunan", "Henan", "Hebei"};
    private static final Map<String, String[]> provinceCityMap = new HashMap<String, String[]>();

    //生产kakfa的数据生产者，模拟生产广告数据
    private KafkaProducer<String, byte[]> kafkaProducer;

    public MockRealTimeData() {

        provinceCityMap.put("Sichuan", new String[]{"Chengdu", "Mianyang"});
        provinceCityMap.put("Hubei", new String[]{"Wuhan", "Jingzhou"});
        provinceCityMap.put("Hunan", new String[]{"Changsha", "Xiangtan"});
        provinceCityMap.put("Henan", new String[]{"Zhengzhou", "Luoyang"});
        provinceCityMap.put("Hebei", new String[]{"Shijiazhuang", "Tangshan"});
        //通过构造方法初始化参数，并构造一个生产者
        kafkaProducer = new KafkaProducer<>(getProducerConfig());
    }

    //创建生产者配置对象，数据序列化类型，以及broker地址，kafka集群broker地址
    private Properties getProducerConfig() {
        //定义一个配置kafka配置对象
        Properties props = new Properties();
        //192.168.1.105:9092,192.168.1.106:9092分别是broker的地址，写2个就可以
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.KAFKA_QUORUMS_PRODUCER);
        //value的序列化类
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Constants.KAFKA_VALUE_CONFIG);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Constants.KAFKA_KEY_CONFIG);
        return props;
    }

    /**
     * run方法产生数据
     */
    public void run() {
        while (true) {

            String province = provinces[random.nextInt(5)];

            String city = provinceCityMap.get(province)[random.nextInt(2)];

            //时间+省份+城市+userid+adid
            String log = new Date().getTime() + "\t" + province + "\t" + city + "\t"
                    + random.nextInt(10) + "\t" + random.nextInt(10);

            //topic:AdRealTimeLog,表示往这个topic发送数据，一个生产者可以往多个producer发送数据
            kafkaProducer.send(new ProducerRecord<String, byte[]>(Constants.KAFKA_TOPICS, log.getBytes()));
            System.out.println(log);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 启动Kafka Producer 往指定的topic写入数据
     *
     * @param args
     */
    public static void main(String[] args) {
        MockRealTimeData producer = new MockRealTimeData();
        producer.start();
    }

}
