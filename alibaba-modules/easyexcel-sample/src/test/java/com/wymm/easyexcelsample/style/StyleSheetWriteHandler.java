package com.wymm.easyexcelsample.style;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheetView;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STSheetViewType;

public class StyleSheetWriteHandler implements SheetWriteHandler {
    
    
    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {
        afterSheetCreate(context.getWriteWorkbookHolder(), context.getWriteSheetHolder());
    }
    
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        SheetWriteHandler.super.afterSheetCreate(writeWorkbookHolder, writeSheetHolder);
        
        Sheet sheet = writeSheetHolder.getSheet();
        PrintSetup printSetup = sheet.getPrintSetup();
        // 设置纸张大小
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
        // 设置横屏
        printSetup.setLandscape(true);
        // 是否显示网格线
        sheet.setDisplayGridlines(false);
        // 打印缩放：所有列打印在一起
        sheet.setFitToPage(true);
        printSetup.setFitHeight((short) 0);
    }
}
