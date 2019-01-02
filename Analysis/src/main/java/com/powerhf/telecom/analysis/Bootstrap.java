package com.powerhf.telecom.analysis;

import com.powerhf.telecom.analysis.tool.AnalysisTool;
import org.apache.hadoop.util.ToolRunner;

public class Bootstrap {
    public static void main(String[] args) throws Exception {
        ToolRunner.run(new AnalysisTool(), args);
    }
}
