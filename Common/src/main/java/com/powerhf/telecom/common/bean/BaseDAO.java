package com.powerhf.telecom.common.bean;

import com.powerhf.telecom.common.constant.FormatConst;
import com.powerhf.telecom.common.util.DateUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import com.powerhf.telecom.common.constant.ValueConst;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 基础数据访问（HBase）对象
 */
public abstract class BaseDAO {
    private ThreadLocal<Connection> connHolder = new ThreadLocal<Connection>();
    private ThreadLocal<Admin> adminHolder = new ThreadLocal<Admin>();

    protected void start() {

        try {
            // 获取线程缓存中的链接对象
            Connection conn = connHolder.get();
            if (conn == null) {
                // 创建默认配置对象
                Configuration conf = HBaseConfiguration.create();
                // 获取链接对象
                conn = ConnectionFactory.createConnection(conf);
                // 将链接对象放置到缓存中去
                connHolder.set(conn);
            }

            Admin admin = adminHolder.get();
            if (admin == null) {
                // 获取管理对象
                admin = conn.getAdmin();
                adminHolder.set(admin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void end() throws Exception {
        // 清空当前线程的缓存对象
        Admin admin = adminHolder.get();
        if (admin != null) {
            admin.close();
            adminHolder.remove();
        }

        Connection conn = connHolder.get();
        if (conn != null) {
            conn.close();
            connHolder.remove();
        }

    }

    /**
     * 创建命名空间，如果命名空间不存在，那么创建新的，否则不做任何操作
     */
    protected void createNamespaceNX(String namespace) throws Exception {

        // 获取命名空间
        /* 错误的写法
        NamespaceDescriptor namespaceDescriptor = admin.getNamespaceDescriptor(namespace);
        if ( namespaceDescriptor == null ) {
            // 创建命名空间
            namespaceDescriptor = NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(namespaceDescriptor);
        }
        */

        Admin admin = adminHolder.get();

        try {
            admin.getNamespaceDescriptor(namespace);
        } catch (NamespaceNotFoundException e) {
            // 创建命名空间
            NamespaceDescriptor namespaceDescriptor =
                    NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(namespaceDescriptor);
        }
    }

    /**
     * 生成分区号
     */
    protected String genRegionNum(String tel, String time, int regionCount) {
        int telHashCode = tel.hashCode();
        int timeHashCode = time.hashCode();

        int hash = Math.abs(telHashCode ^ timeHashCode);

        int regionNum = hash % regionCount;

        //返回分区号
        return regionNum + "";
    }

    /**
     * 创建默认表
     */
    protected void createTableXX(String table,String coprocessorClassName, String... columnFamilies) throws Exception {
        createTableXX(table, coprocessorClassName,ValueConst.REGION_DEFAULT_COUNT, columnFamilies);
    }

    /**
     * 创建表，如果表已经存在，那么删除后再增加，否则直接增加
     */
    protected void createTableXX(String table,String clazzName, int reginCount, String... columnFamilies) throws Exception {

        Admin admin = adminHolder.get();
        TableName tableName = TableName.valueOf(table);

        // 判断表是否存在
        if (admin.tableExists(tableName)) {

            // 禁用表
            admin.disableTable(tableName);
            // 表已经存在,删除表
            admin.deleteTable(tableName);
        }

        // 创建表格描述器

        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);

        if (columnFamilies != null && columnFamilies.length != 0) {
            for (String columnFamily : columnFamilies) {
                tableDescriptor.addFamily(new HColumnDescriptor(columnFamily));
            }
        }

        //增加协处理器
        if (clazzName != null && !"".equals(clazzName.trim())) {
            tableDescriptor.addCoprocessor(clazzName);
        }
        // 创建表
        admin.createTable(tableDescriptor, genSplitKeys(reginCount));
    }

    /**
     * 生成分区键
     *
     * @param reginCount
     * @return
     */
    protected byte[][] genSplitKeys(int reginCount) {
        int splitKeySize = reginCount - 1;

        byte[][] keys = new byte[splitKeySize][];

        List<byte[]> splitKeyList = new ArrayList<byte[]>();

        for (int i = 0; i < splitKeySize; i++) {
            splitKeyList.add(Bytes.toBytes(i + "|"));
        }
        splitKeyList.toArray(keys);
        return keys;
    }

    /**
     * 增加数据
     *
     * @param tableString
     * @param put
     */
    protected void putData(String tableString, Put put) throws Exception {

        TableName tableName = TableName.valueOf(tableString);
        // 获取链接对象
        Connection connection = connHolder.get();
        // 获取表对象
        Table table = connection.getTable(tableName);

        table.put(put);

        table.close();

    }

    /**
     * 获取始末rowkey
     */
    public List<String[]> getStartStopRowkeys(String tel, String start, String end) {
        List<String[]> rowkeys = new ArrayList<String[]>();
        // start = 201810 ~ 201902
        // 201801 ~ 201810|
        try {
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(DateUtil.parse(start, FormatConst.DATE_YM));

            Calendar endDate = Calendar.getInstance();
            endDate.setTime(DateUtil.parse(end, FormatConst.DATE_YM));

            while (startDate.getTimeInMillis() <= endDate.getTimeInMillis()) {
                //201810
                String startRowkey = DateUtil.format(startDate.getTime(), FormatConst.DATE_YM);
                String regionNum = genRegionNum(tel, startRowkey, ValueConst.REGION_DEFAULT_COUNT);

                startRowkey = regionNum + "_" + tel + "_" + startRowkey;
                String stopRowkey = startRowkey + "|";

                String[] rowkeyRange = {startRowkey, stopRowkey};
                rowkeys.add(rowkeyRange);

                startDate.add(Calendar.MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rowkeys;

    }

}
