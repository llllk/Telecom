package com.powerhf.telecom.analysis.bean;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 封装value数据到bean
 */
public class AnalysisValue implements Writable {

    private int sumCount;
    private int sumDuration;

    public AnalysisValue() {
    }

    public AnalysisValue(int sumCount, int sumDuration) {
        this.sumCount = sumCount;
        this.sumDuration = sumDuration;
    }

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }

    public int getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(int sumDuration) {
        this.sumDuration = sumDuration;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(sumCount);
        out.writeInt(sumDuration);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        sumCount = in.readInt();
        sumDuration = in.readInt();
    }
}
