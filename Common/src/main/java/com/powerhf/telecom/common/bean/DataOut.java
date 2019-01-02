package com.powerhf.telecom.common.bean;

import java.io.Closeable;

/**
 * 数据输出
 */
public interface DataOut extends Closeable {

    public void write(Object obj) throws Exception;

    public void write(String string) throws Exception;


}
