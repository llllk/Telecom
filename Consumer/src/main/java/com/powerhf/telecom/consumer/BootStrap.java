package com.powerhf.telecom.consumer;

import com.powerhf.telecom.common.bean.Consumer;
import com.powerhf.telecom.consumer.bean.Flume2KafkaConsumer;

import java.io.IOException;

public class BootStrap {
    public static void main(String[] args) throws IOException {

        //创建消费者对象
        Consumer consumer = new Flume2KafkaConsumer();
        //消费数据
        consumer.Consume();
        //关闭资源
        consumer.close();

    }
}
