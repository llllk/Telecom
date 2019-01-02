package com.powerhf.telecom.analysis.mapper;

import com.powerhf.telecom.analysis.bean.AnalysisKey;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class AnalysisBeanMapper extends TableMapper<AnalysisKey, Text> {
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        String rowkey = Bytes.toString(key.get());

        String[] values = rowkey.split("_");
        // 5_1333_20180201x_14444_0100_1
        String call1 = values[1];
        String call2 = values[3] ;
        String calltime = values[2];
        String duration = values[4];
        String flg = values[5];

        String year = calltime.substring(0,4);
        String month = calltime.substring(0,6);
        String day = calltime.substring(0, 8);
        //主叫
        context.write(new AnalysisKey(call1, year), new Text(duration));
        context.write(new AnalysisKey(call1, month), new Text(duration));
        context.write(new AnalysisKey(call1, day), new Text(duration));

        context.write(new AnalysisKey(call2, year), new Text(duration));
        context.write(new AnalysisKey(call2, month), new Text(duration));
        context.write(new AnalysisKey(call2, day), new Text(duration));
    }
}
