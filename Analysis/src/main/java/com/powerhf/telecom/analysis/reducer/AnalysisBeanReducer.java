package com.powerhf.telecom.analysis.reducer;

import com.powerhf.telecom.analysis.bean.AnalysisKey;
import com.powerhf.telecom.analysis.bean.AnalysisValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class AnalysisBeanReducer extends Reducer<AnalysisKey, Text,AnalysisKey, AnalysisValue> {
    @Override
    protected void reduce(AnalysisKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sumCount = 0;
        int sumDuration = 0;

        for (Text value : values) {
            sumDuration = sumDuration + Integer.parseInt(value.toString());
            sumCount++;
        }

        context.write(key, new AnalysisValue(sumCount, sumDuration));
    }
}
