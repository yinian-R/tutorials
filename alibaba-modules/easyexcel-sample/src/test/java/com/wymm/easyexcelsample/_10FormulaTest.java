package com.wymm.easyexcelsample;

import com.alibaba.excel.EasyExcel;
import com.wymm.easyexcelsample.excel.TestFileUtil;
import com.wymm.easyexcelsample.excel.formula.FormulaSimple;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 常用公式
 */
public class _10FormulaTest {
    
    @Test
    void simple(){
        String fileName = TestFileUtil.getPath() + "Formula-simple" + System.currentTimeMillis() + ".xlsx";
        
        List<FormulaSimple> formulaSimples = TestFileUtil.formulaData();
        
        
        EasyExcel.write(fileName)
                .head(FormulaSimple.class)
                .sheet("公式")
                .doWrite(formulaSimples);
        
        System.out.println(fileName);
    }

}
