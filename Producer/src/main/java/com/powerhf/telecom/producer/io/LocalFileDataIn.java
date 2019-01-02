package com.powerhf.telecom.producer.io;

import com.powerhf.telecom.common.bean.Data;
import com.powerhf.telecom.common.bean.DataIn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地文件输入
 */
public class LocalFileDataIn implements DataIn {
    private BufferedReader reader;

    public LocalFileDataIn(String path) {
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object read() throws Exception {
        return null;
    }

    @Override
    public <T extends Data> List<T> read(Class<T> clazz) throws Exception {
        List<T> objecs = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            T t = clazz.newInstance();
            t.setContect(line);
            objecs.add(t);
        }
        return objecs;
    }



    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
