package com.wymm.springeasyexcelsample.web.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.poi.excel.ExcelPicUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.wymm.springeasyexcelsample.web.entity.excel.BookExcel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 支持读取浮动的图片
 * @param <T>
 */
@Slf4j
public abstract class ImageReadListener<T> implements ReadListener<T> {
    
    /**
     * 图片缓存Map，key: sheetNo_rowIndex_colIndex
     */
    private Map<String, PictureData> picMap = new HashMap<>();
    
    private MultipartFile file;
    
    public ImageReadListener(MultipartFile file) {
        Assert.notNull(file);
        this.file = file;
    }
    
    @SneakyThrows
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        Integer sheetNo = context.readSheetHolder().getSheetNo();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Map<String, PictureData> tempMap = ExcelPicUtil.getPicMap(workbook, sheetNo);
        for (String key : tempMap.keySet()) {
            picMap.put(sheetNo + "_" + key, tempMap.get(key));
        }
        IoUtil.close(workbook);
    }
    
    @Override
    public void invoke(T data, AnalysisContext context) {
        Integer sheetNo = context.readSheetHolder().getSheetNo();
        Integer rowIndex = context.readRowHolder().getRowIndex();
        invoke(data, context, picMap, sheetNo, rowIndex);
    }
    
    public abstract void invoke(T data, AnalysisContext context, Map<String, PictureData> picMap, Integer sheetNo, Integer rowIndex);
    
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    
    }
}
    
