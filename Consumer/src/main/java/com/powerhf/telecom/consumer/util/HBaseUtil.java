package com.powerhf.telecom.consumer.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

public class HBaseUtil {
    public static boolean isExistTable(Configuration conf,String tableName) {
        HBaseAdmin admin = null;
        try {
            admin = new HBaseAdmin(conf);
            return admin.tableExists(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (admin != null) {
                    admin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }






}
