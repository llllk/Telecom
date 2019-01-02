package com.powerhf.telecom.producer.io;

import com.powerhf.telecom.common.bean.DataOut;

import java.io.*;

/**
 * 本地文件输出
 */
public class LocalFileDataOut implements DataOut {
    private PrintWriter writer;

    @Override
    public void write(Object obj) throws Exception {
        write(obj.toString());
    }

    @Override
    public void write(String string) throws Exception {
        writer.println(string);
        writer.flush();
    }

    public LocalFileDataOut(String path) {
        try {
       writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }

    }
}
