package com.powerhf.telecom.analysis.bean;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 封装key数据到bean
 */
public class AnalysisKey implements WritableComparable<AnalysisKey> {

    private String tel;
    private String time;

    public AnalysisKey(String tel, String time) {
        this.tel = tel;
        this.time = time;
    }

    public AnalysisKey() {
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(tel);
        out.writeUTF(time);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        tel = in.readUTF();
        time = in.readUTF();
    }

    @Override
    public int compareTo(AnalysisKey analysisKey) {
        int result = tel.compareTo(analysisKey.tel);
        if (result == 0) {
            result = time.compareTo(analysisKey.time);
        }
        return result;
    }
}
