package com.powerhf.telecom.common.bean;

import java.io.Closeable;
import java.util.List;

/**
 * 数据输入接口
 */
public interface DataIn extends Closeable {
    public Object read() throws Exception;

    public <T extends Data> List<T> read(Class<T> clazz) throws Exception;
}
