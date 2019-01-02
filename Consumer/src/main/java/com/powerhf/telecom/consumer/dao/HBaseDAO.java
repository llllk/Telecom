package com.powerhf.telecom.consumer.dao;

import com.powerhf.telecom.common.api.ColumnRef;
import com.powerhf.telecom.common.api.RowkeyRef;
import com.powerhf.telecom.common.api.TableRef;
import com.powerhf.telecom.common.bean.BaseDAO;
import com.powerhf.telecom.common.constant.NameConst;
import com.powerhf.telecom.common.constant.ValueConst;
import com.powerhf.telecom.consumer.bean.Calllog;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Field;

/**
 * HBase数据访问对象
 * 预分区
 * 分区键
 * 分区号
 *rowkey设计
 */
public class HBaseDAO extends BaseDAO {
    /**
     * 初始化
     */
    public void init() throws Exception {

        start();
        // 创建命名空间
        createNamespaceNX(NameConst.NAMESPACE);

        // 创建表
        createTableXX(NameConst.TABLE, "com.powerhf.telecom.consumer.coprocessor.InsertCalleeCoprocessor", NameConst.COLUMN_FAMILY_CALLER,NameConst.COLUMN_FAMILY_CALLEE);

        end();
    }


    public void genRowkey(Calllog calllog, String flg) {
        String rowkey = genRegionNum(calllog.getCall1(), calllog.getCalltime().substring(0, 6), ValueConst.REGION_DEFAULT_COUNT)
                + "_" + calllog.getCall1() + "_" + calllog.getCalltime() + "_" + calllog.getCall2() + "_" + calllog.getDuration() + "_" + flg;
        calllog.setRowkey(rowkey);
    }

    public void putData(Object obj) throws Exception {
        start();

        //获取对象对应的类型（利用反射）
        Class clazz = obj.getClass();
        //获取类型数据中的注解对象
        TableRef tableRef = (TableRef) clazz.getAnnotation(TableRef.class);
        String tableName = tableRef.value();

        //获取类型对象中的所有属性
        String rowkey = "";
        Field[] fields = clazz.getDeclaredFields();
        //遍历属性，获取rowkey
        for (Field field : fields) {
            RowkeyRef rowkeyRef = field.getAnnotation(RowkeyRef.class);
            //判断获取的rowkey是否为空，如果不为空，获取属性值作为rowkey
            if (rowkeyRef != null) {
                //获取属性值
                field.setAccessible(true);
                rowkey = (String) field.get(obj);
                break;
            }
        }
        //创建put对象
        Put put = new Put(Bytes.toBytes(rowkey));
        //获取对象类型中所有的成员属性，包括私有属性
        Field[] colFields = clazz.getDeclaredFields();
        //遍历属性
        for (Field field : colFields) {
            //获取列属性
            ColumnRef columnRef = field.getAnnotation(ColumnRef.class);
            //判断列属性是否为空
            if (columnRef != null) {
                //获取列族
                String columnFamily = columnRef.family();
                //获取列
                String column = columnRef.column();
                //如果列为空，获取属性名作为列名
                if (column == null || "".equals(column.trim())) {
                    column = field.getName();
                }
                field.setAccessible(true);
                String value = (String) field.get(obj);

                put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
            }
        }
        putData(tableName,put);
        end();

    }
    /**
     * 增加数据
     * rowkey设计
     * 唯一性原则
     * 长度原则
     * 散列原则
     * 满足业务需求
     */
   /* public void put(String data) throws Exception {
        start();
        String[] split = data.split("\t");

        String call1 = split[0];
        String call2 = split[1];
        String calltime = split[2];
        String duration = split[3];

        String regionNum = genRegionNum(call1, calltime, ValueConst.REGION_DEFAULT_COUNT);
        String rowKye = regionNum + "_" + call1 + "_" + calltime + "_" + call2 + "_" + duration;

        Put put = new Put(Bytes.toBytes(rowKye));
        put.addColumn(Bytes.toBytes(NameConst.COLUMN_FAMILY_INFO), Bytes.toBytes("call1"), Bytes.toBytes(call1));
        put.addColumn(Bytes.toBytes(NameConst.COLUMN_FAMILY_INFO), Bytes.toBytes("call2"), Bytes.toBytes(call2));
        put.addColumn(Bytes.toBytes(NameConst.COLUMN_FAMILY_INFO), Bytes.toBytes("calltime"), Bytes.toBytes(calltime));
        put.addColumn(Bytes.toBytes(NameConst.COLUMN_FAMILY_INFO), Bytes.toBytes("duration"), Bytes.toBytes(duration));

        putData(NameConst.TABLE, put);

        end();
    }*/

}
