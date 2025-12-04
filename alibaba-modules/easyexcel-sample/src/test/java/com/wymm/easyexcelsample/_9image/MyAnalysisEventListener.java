package com.wymm.easyexcelsample._9image;

import cn.hutool.poi.excel.ExcelPicUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.util.Map;

public class MyAnalysisEventListener extends AnalysisEventListener {
    @Override
    public void invoke(Object data, AnalysisContext context) {
    
    }
    
    @SneakyThrows
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println();
        ReadWorkbookHolder readWorkbookHolder = context.readWorkbookHolder();
        ReadWorkbook readWorkbook = readWorkbookHolder.getReadWorkbook();
        //  web 启动时读取不到数据
        File inputStream = readWorkbook.getFile();
        
        Workbook workbook = WorkbookFactory.create(inputStream);
        Map<String, PictureData> picMap = ExcelPicUtil.getPicMap(workbook, 0);
        workbook.close();
        System.out.println();
    }
}
