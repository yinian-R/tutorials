package com.wymm.easyexcelsample.excel;

import com.alibaba.excel.util.ListUtils;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public abstract class TestFileUtil {
    
    private static final String TARGET_PATH = System.getProperty("user.dir") + File.separator + "target" + File.separator;
    private static final String RESOURCE_PATH = String.join(File.separator, System.getProperty("user.dir"),
            "src", "main", "resources", "excel")
            + File.separator;
    
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
    
    public static String getPath() {
        return TARGET_PATH;
    }
    
    public static String getResourcePath() {
        return RESOURCE_PATH;
    }
    
}
