package com.wymm.easyexcelsample.excel;

import com.alibaba.excel.util.ListUtils;
import com.wymm.easyexcelsample.excel.formula.FormulaSimple;

import java.util.Date;
import java.util.List;

public abstract class TestFileUtil {
    
    public static List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
    
    public static List<FormulaSimple> formulaData() {
        List<FormulaSimple> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            FormulaSimple data = new FormulaSimple();
            data.setVideoCell("temp.mp4");
            list.add(data);
        }
        return list;
    }
    
    public static String getPath() {
        return TestFileUtil.class.getResource("/").getPath();
    }
    
}
