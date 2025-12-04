package com.wymm.easyexcelsample.excel.formula;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.metadata.data.FormulaData;
import com.alibaba.excel.metadata.data.WriteCellData;
import lombok.Data;

@Data
public class FormulaSimple {
    
    @ExcelProperty("序号")
    @ColumnWidth(10)
    private WriteCellData<String> idx;
    
    
    @ExcelProperty("视频文件")
    @ColumnWidth(10)
    private WriteCellData<String> videoCell;
    
    
    public FormulaSimple() {
        WriteCellData<String> formula = new WriteCellData<>();
        FormulaData formulaData = new FormulaData();
        formulaData.setFormulaValue("ROW()-1");
        formula.setFormulaData(formulaData);
        idx = formula;
    }
    
    /**
     * 超链接 导出excel.xlsx 同目录下 ./files/videoFileName 文件。可在excel中点击打开
     * @param videoFileName
     */
    public void setVideoCell(String videoFileName) {
        if (StrUtil.isEmpty(videoFileName)) {
            return;
        }
        WriteCellData<String> formula = new WriteCellData<>();
        FormulaData formulaData = new FormulaData();
        formulaData.setFormulaValue("HYPERLINK(\"./files/" + videoFileName + "\")");
        formula.setFormulaData(formulaData);
        this.videoCell = formula;
    }
    
}
