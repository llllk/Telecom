package com.powerhf.telecom.common.bean;

/**
 * 数据抽象类Data：所有传入数据必须继承该类
 * 包含get和set方法
 */

public abstract  class Data {
    private String contect;
    private String rowkey;

    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public String getContect() {
        return contect;
    }

    public void setContect(String contect) {
        this.contect = contect;
    }

}
