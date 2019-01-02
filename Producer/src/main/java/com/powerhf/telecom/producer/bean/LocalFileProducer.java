package com.powerhf.telecom.producer.bean;

import com.powerhf.telecom.common.bean.DataIn;
import com.powerhf.telecom.common.bean.DataOut;
import com.powerhf.telecom.common.bean.Producer;
import com.powerhf.telecom.common.constant.FormatConst;
import com.powerhf.telecom.common.util.DateUtil;
import com.powerhf.telecom.common.util.NumberUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 本地文件生产者
 * 将数据生产到本地日志文件中
 */
public class LocalFileProducer implements Producer {

    private DataIn dataIn;
    private DataOut dataOut;
    private volatile boolean flg = true;

    @Override
    public void setIn(DataIn in) {
        this.dataIn = in;
    }

    @Override
    public void setOut(DataOut out) {
        this.dataOut = out;
    }

    /**
     * 生产数据
     */
    @Override
    public void produce() {
        try{
            List<Contact> contacts = dataIn.read(Contact.class);
            dataIn.close();

            while (flg) {
                //随机生成两个通话用户
                int call1Index = new Random().nextInt(contacts.size());
                int call2Index;

                while (true) {
                    call2Index = new Random().nextInt(contacts.size());
                    if (call2Index != call1Index) {
                        break;
                    }
                }

                Contact call1 = contacts.get(call1Index);
                Contact call2 = contacts.get(call2Index);

                //随机生成通话时间
                String startDate = "2018101000000";
                String endDate = "2019101000000";

                long startTime = DateUtil.parse(startDate, FormatConst.DATE_YMDHMS).getTime();
                long endTime = DateUtil.parse(endDate, FormatConst.DATE_YMDHMS).getTime();

                //通话时间
                long callTime =startTime + (long)((endTime - startTime) * Math.random());
                String callTimeString = DateUtil.format(new Date(callTime), FormatConst.DATE_YMDHMS);

                //随机生成通话时长
                int duration = new Random().nextInt(1800);
                String durationString = NumberUtil.format(duration, 4);
                //封装对象
                Calllog log = new Calllog(call1.getTel(),call2.getTel(),callTimeString,durationString);

                System.out.println(log);
                //将通话信息输出到指定文件中
                dataOut.write(log);
                //每秒生成10条数据
                Thread.sleep(100);

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
        if (dataIn != null) {
            dataIn.close();
        }
        if (dataOut != null) {
            dataOut.close();
        }
    }
}
