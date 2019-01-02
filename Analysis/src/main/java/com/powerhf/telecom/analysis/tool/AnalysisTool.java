package com.powerhf.telecom.analysis.tool;

import com.powerhf.telecom.analysis.bean.AnalysisKey;
import com.powerhf.telecom.analysis.bean.AnalysisValue;
import com.powerhf.telecom.analysis.io.AnalysisBeanOutputFormat;
import com.powerhf.telecom.analysis.mapper.AnalysisBeanMapper;
import com.powerhf.telecom.analysis.reducer.AnalysisBeanReducer;
import com.powerhf.telecom.common.constant.NameConst;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.util.Tool;

public class AnalysisTool implements Tool {
    @Override
    public int run(String[] args) throws Exception {
       //Mapreduce
        Job job = Job.getInstance();
        job.setJarByClass(AnalysisTool.class);



        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes(NameConst.COLUMN_FAMILY_CALLER));

        //mapper
        TableMapReduceUtil.initTableMapperJob(
                NameConst.TABLE,
                scan,
                AnalysisBeanMapper.class,
                AnalysisKey.class,
                Text.class,
                job
        );
        //reducer
        job.setReducerClass(AnalysisBeanReducer.class);
        job.setOutputKeyClass(AnalysisKey.class);
        job.setOutputValueClass(AnalysisValue.class);

        //outputformat
        job.setOutputFormatClass(AnalysisBeanOutputFormat.class);

        boolean flg = job.waitForCompletion(true);
        //magic
        if (flg) {
            return JobStatus.State.SUCCEEDED.getValue();
        } else {
            return JobStatus.State.FAILED.getValue();
        }

    }

    @Override
    public void setConf(Configuration conf) {

    }

    @Override
    public Configuration getConf() {
        return null;
    }
}
