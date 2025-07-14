package com.wymm.easyexcelsample._9image;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelPicUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.fastjson.JSON;
import com.wymm.easyexcelsample.excel.TestFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class _9ImageTest {
    
    /**
     * poi 能读取到浮动的图片
     * 坐标系可通过 XSSFClientAnchor 获得Row1、Row2、Col1、Col2，获取到的坐标系是图片右下角占用单元格的坐标系
     */
    @Test
    public void excelPicRead() {
        String file = TestFileUtil.getPath() + "image" + File.separator + "浮动和嵌套单元格图片.xlsx";
        
        
        ExcelReader excelReader = ExcelUtil.getReader(file);
        
        List<List<Object>> read = excelReader.read();
        System.out.println(read);
        
        Map<Integer, List<CustomExcelPicUtil.ExcelPic>> excelPicMap = CustomExcelPicUtil.getExcelPic(excelReader.getWorkbook(),
                excelPic -> {
                    // todo ...... 自己实现读取到这个图片时做啥
                }
        );
        
        String rootPath = TestFileUtil.getPath() + "image\\";
        excelPicMap.values().forEach(excelPics -> excelPics.forEach(excelPic -> {
            CustomExcelPicUtil.writePicByteTo(excelPic.getData(), rootPath + excelPic.getRowIdx() + "-" + excelPic.getColIdx() + "." + excelPic.getSuffix());
        }));
        
        System.out.println(excelPicMap.size());
    }
    
    /**
     * poi  写入图片到 excel
     */
    @Test
    public void excelPicWrite() {
        ExcelWriter excelWriter = ExcelUtil.getWriter();
        String picPath = TestFileUtil.getPath() + "image" + File.separator + "img.png";
        byte[] demoData = FileUtil.readBytes(picPath);
        CustomExcelPicUtil.writePicToExcel(excelWriter, Arrays.asList(
                CustomExcelPicUtil.ExcelPicWrite.builder()
                        .picType(Workbook.PICTURE_TYPE_JPEG)
                        .data(demoData)
                        .rowIdx(1)
                        .colIdx(3)
                        .build(),
                CustomExcelPicUtil.ExcelPicWrite.builder()
                        .picType(Workbook.PICTURE_TYPE_JPEG)
                        .data(demoData)
                        .rowIdx(2)
                        .colIdx(3).build(),
                CustomExcelPicUtil.ExcelPicWrite.builder()
                        .picType(Workbook.PICTURE_TYPE_JPEG)
                        .data(demoData)
                        .rowIdx(3)
                        .colIdx(3)
                        .build()
        ));
        
        excelWriter.flush(new File(TestFileUtil.getPath() + "image" + File.separator + "poi写入图片.xlsx"));
        excelWriter.close();
    }
    
    
    /**
     * 使用 hutool 读取浮动图片
     */
    @Test
    public void hutoolExcelPicRead() {
        String file = TestFileUtil.getPath() + "image" + File.separator + "浮动和嵌套单元格图片.xlsx";
        
        
        ExcelReader excelReader = ExcelUtil.getReader(file);
        
        List<List<Object>> read = excelReader.read();
        System.out.println(read);
        
        Map<String, PictureData> picMap = ExcelPicUtil.getPicMap(excelReader.getWorkbook(), 0);
        
        System.out.println(picMap.size());
    }
    
    
    /**
     * 读取浮动单元格的图片
     */
    @Test
    public void read1() {
        String fileName = TestFileUtil.getPath() + "image" + File.separator + "浮动和嵌套单元格图片.xlsx";
        
        // 这里 也可以不指定class，返回一个list，然后读取第一个sheet 同步读取会自动finish
        List<Map<Integer, String>> listMap = EasyExcel.read(fileName)
                .sheet()
                .registerReadListener(new MyAnalysisEventListener())
                .doReadSync();
        for (Map<Integer, String> data : listMap) {
            // 返回每条数据的键值对 表示所在的列 和所在列的值
            log.info("读取到数据:{}", JSON.toJSONString(data));
        }
    }
    
}
