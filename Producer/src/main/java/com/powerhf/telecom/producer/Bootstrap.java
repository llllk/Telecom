package com.powerhf.telecom.producer;

import com.powerhf.telecom.common.bean.Producer;
import com.powerhf.telecom.producer.bean.LocalFileProducer;
import com.powerhf.telecom.producer.io.LocalFileDataIn;
import com.powerhf.telecom.producer.io.LocalFileDataOut;

import java.io.IOException;

/**
 *  生产者启动对象
 *
 *  1.面向接口：增加扩展性
 *  2.泛型
 *  3.流
 *  4.乱码
 *  5.重写，重载
 *  6.反射
 */
public class Bootstrap {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("请输入正确的参数，格式为 java Bootstrap path1 path2");
            return;
        }
        //构建生产者对象
        Producer producer =  new LocalFileProducer();
        LocalFileDataIn localFileDataIn = new LocalFileDataIn(args[0]);
        LocalFileDataOut localFileDataOut = new LocalFileDataOut(args[1]);
        producer.setIn(localFileDataIn);
        producer.setOut(localFileDataOut);

        producer.produce();

        producer.close();
    }
}
