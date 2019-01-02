package com.powerhf.telecom.consumer.bean;

import com.powerhf.telecom.common.bean.Consumer;
import com.powerhf.telecom.consumer.dao.HBaseDAO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Flume2KafkaConsumer implements Consumer {
    /**
     * 消费数据
     */
    @Override
    public void Consume() {

        try {
            //创建配置对象
            Properties properties = new Properties();
            //应用类加载器：classpath
            properties.load(Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("kafka.properties"));
            //创建消费者对象
            KafkaConsumer consumer = new KafkaConsumer(properties);
            //订阅主题
            consumer.subscribe(Arrays.asList("calllog"));
            //构建DAO对象
            HBaseDAO dao = new HBaseDAO();
            //初始化
            dao.init();

            while (true) {
                //获取消费的数据
                ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
                //遍历每一条数据
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    System.out.println(consumerRecord.value());
                    //增加数据
                   /* dao.put(consumerRecord.value());*/

                    //分解数据
                    String[] values = consumerRecord.value().split("\t");
                    Calllog calllog = new Calllog(values[0], values[1], values[2], values[3]);
                    dao.genRowkey(calllog, "1");
                    //增加对象（主叫数据）
                    dao.putData(calllog);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 释放资源
     * @throws IOException
     */
    @Override
    public void close() throws IOException {

    }
}
