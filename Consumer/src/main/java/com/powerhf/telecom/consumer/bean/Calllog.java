package com.powerhf.telecom.consumer.bean;

import com.powerhf.telecom.common.api.ColumnRef;
import com.powerhf.telecom.common.api.RowkeyRef;
import com.powerhf.telecom.common.api.TableRef;
import com.powerhf.telecom.common.bean.Data;
import com.powerhf.telecom.common.constant.NameConst;

@TableRef("powerhf:calllog")
public class Calllog extends Data {
    @RowkeyRef
    private String rowkey;
    @ColumnRef(family = NameConst.COLUMN_FAMILY_CALLER)
    private String call1;
    @ColumnRef(family = NameConst.COLUMN_FAMILY_CALLER)
    private String call2;
    @ColumnRef(family = NameConst.COLUMN_FAMILY_CALLER)
    private String calltime;
    @ColumnRef(family = NameConst.COLUMN_FAMILY_CALLER)
    private String duration;

    public Calllog() {
    }

    public Calllog(String call1, String call2, String calltime, String duration) {
        this.call1 = call1;
        this.call2 = call2;
        this.calltime = calltime;
        this.duration = duration;

    }

    @Override
    public String getRowkey() {
        return rowkey;
    }

    @Override
    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public String getCall1() {
        return call1;
    }

    public void setCall1(String call1) {
        this.call1 = call1;
    }

    public String getCall2() {
        return call2;
    }

    public void setCall2(String call2) {
        this.call2 = call2;
    }

    public String getCalltime() {
        return calltime;
    }

    public void setCalltime(String calltime) {
        this.calltime = calltime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}