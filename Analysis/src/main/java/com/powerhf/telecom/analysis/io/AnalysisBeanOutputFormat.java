package com.powerhf.telecom.analysis.io;


import com.powerhf.telecom.analysis.bean.AnalysisKey;
import com.powerhf.telecom.analysis.bean.AnalysisValue;
import com.powerhf.telecom.common.util.JDBCUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AnalysisBeanOutputFormat extends OutputFormat<AnalysisKey, AnalysisValue> {

    private class MySQLRecordWriter extends RecordWriter<AnalysisKey, AnalysisValue> {

        private Connection conn;

        public MySQLRecordWriter() {
            conn = JDBCUtil.getConnection();
        }

        /**
         * 向mysql中写入数据
         * @param key
         * @param value
         * @throws IOException
         * @throws InterruptedException
         */
        public void write(AnalysisKey key, AnalysisValue value) throws IOException, InterruptedException {

            // 向Mysql中插入数据
            String insertSql = "insert into t_calllog(tel, calltime, sumcount, sumduration) values ( ?, ?, ?, ? )";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = conn.prepareStatement(insertSql);
                preparedStatement.setString(1, key.getTel());
                preparedStatement.setString(2, key.getTime());
                preparedStatement.setInt(3, value.getSumCount());
                preparedStatement.setInt(4, value.getSumDuration());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        /**
         * 释放资源
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            if ( conn != null ) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public RecordWriter<AnalysisKey, AnalysisValue> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new MySQLRecordWriter();
    }

    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {

    }
    private FileOutputCommitter committer = null;
    public static Path getOutputPath(JobContext job) {
        String name = job.getConfiguration().get(FileOutputFormat.OUTDIR);
        return name == null ? null: new Path(name);
    }

    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        if (committer == null) {
            Path output = getOutputPath(context);
            committer = new FileOutputCommitter(output, context);
        }
        return committer;
    }
}
