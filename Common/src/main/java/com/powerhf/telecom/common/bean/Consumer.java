package com.powerhf.telecom.common.bean;

import java.io.Closeable;

public interface Consumer extends Closeable {
    public void Consume();
}
