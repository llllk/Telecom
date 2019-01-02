package com.powerhf.telecom.consumer.coprocessor;

import com.powerhf.telecom.common.bean.BaseDAO;
import com.powerhf.telecom.common.constant.NameConst;
import com.powerhf.telecom.common.constant.ValueConst;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * 增加被叫用户协处理器
 *
 * 继承协处理器类
 * 重写对应的方法 postPut
 * 将协处理器和表做关联
 * 将协处理器打包放置到服务器中（将依赖的jar包也要同时上传，并且需要分发）
 */
public class InsertCalleeCoprocessor extends BaseRegionObserver {
    /**
     * 在增加主叫数据后插入被叫数据
     */
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        String callerRowkey = Bytes.toString(put.getRow());
// 5_18840172592_20180110002546_14930423697_1010_1
        String[] values = callerRowkey.split("_");

        String call1 = values[3];
        String call2 = values[1];
        String calltime = values[2];
        String duration = values[4];
        String flg = values[5];

        if (flg.equals("1")) {
            CoprocessorDao dao = new CoprocessorDao();
            String rowkey = dao.getRegionNum(call1, calltime.substring(0, 6), ValueConst.REGION_DEFAULT_COUNT) + "_" + call1 + "_" + calltime + "_" + call2 + "_" + duration + "_0";
            Put calleePut = new Put(Bytes.toBytes(rowkey));
            calleePut.addColumn(Bytes.toBytes(NameConst.COLUMN_FAMILY_CALLEE), Bytes.toBytes("call1"), Bytes.toBytes(call1));
            calleePut.addColumn(Bytes.toBytes(NameConst.COLUMN_FAMILY_CALLEE), Bytes.toBytes("call2"), Bytes.toBytes(call1));
            calleePut.addColumn(Bytes.toBytes(NameConst.COLUMN_FAMILY_CALLEE), Bytes.toBytes("calltime"), Bytes.toBytes(calltime));
            calleePut.addColumn(Bytes.toBytes(NameConst.COLUMN_FAMILY_CALLEE), Bytes.toBytes("duration"), Bytes.toBytes(duration));
            //增加被叫数据
            TableName tableName = TableName.valueOf(NameConst.TABLE);
            Table table = e.getEnvironment().getTable(tableName);
            table.put(calleePut);
            table.close();
        }
    }
  private   class CoprocessorDao extends BaseDAO {
        public String getRegionNum(String tel, String date, int regionCount) {
            return genRegionNum(tel, date, regionCount);
        }
}


}

