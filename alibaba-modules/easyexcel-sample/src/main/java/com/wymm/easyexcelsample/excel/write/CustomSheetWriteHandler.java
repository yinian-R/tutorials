package com.wymm.easyexcelsample.excel.write;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.List;

/**
 * 自定义拦截器.对第一列第一行和第二行的数据新增下拉框，显示 测试1 测试2
 */
@Slf4j
public class CustomSheetWriteHandler implements SheetWriteHandler {
    
    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {
        log.info("第{}个Sheet写入成功。", context.getWriteSheetHolder().getSheetNo());
    
        DataValidationHelper helper = context.getWriteSheetHolder().getSheet().getDataValidationHelper();
    
        // 1 区间设置 第一列第一行和第二行的数据。由于第一行是头，所以第一、二行的数据实际上是第二三行
        //CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 2, 0, 0);
        // 2 区间设置，设置整列下拉框
        //CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(-1, -1, 0, 0);
        // 3 区间设置，设置整列下拉框（除第一行标题外）；最大行数 Ctrl + 下查看，代码中从0开始，所以需要-1
        //CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 1048575, 0, 0);
        // 4 区间设置，除标题外其它单元格设置下拉框
        List<List<String>> head = context.getWriteSheetHolder().getHead();
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 1048575, 0, 0);
        
        DataValidationConstraint constraint = helper.createExplicitListConstraint(new String[]{"测试1", "测试2"});
        DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
        context.getWriteSheetHolder().getSheet().addValidationData(dataValidation);
    }
}
