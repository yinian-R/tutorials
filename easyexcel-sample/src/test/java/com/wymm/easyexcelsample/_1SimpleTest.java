package com.wymm.easyexcelsample;

import com.alibaba.excel.EasyExcel;
import com.wymm.easyexcelsample.excel.DemoData;
import com.wymm.easyexcelsample.excel.TestFileUtil;
import com.wymm.easyexcelsample.excel.simple.DemoDataListener;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Collections;

/**
 * 简单示例
 */
public class _1SimpleTest {
    
    /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 直接读即可
     */
    @Test
    public void simpleRead() {
        InputStream inputStream = this.getClass().getResourceAsStream("/simple/demo.xlsx");
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, DemoData.class, new DemoDataListener())
                .sheet()
                .doRead();
    }
    
    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        String fileName = TestFileUtil.getPath() + "write" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class)
                .sheet("模板")
                .doWrite(TestFileUtil.data());
    }
    
}
