package com.wymm.easyexcelsample;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wymm.easyexcelsample.excel.merger.BackRouteDTO;
import com.wymm.easyexcelsample.excel.merger.strategy.MergeStrategy;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class _5MergeTest {
    ObjectMapper objectMapper = new ObjectMapper();
    
    
    /**
     * 自定义合并策略
     */
    @Test
    void merge() {
        MergeStrategy mergeStrategy = new MergeStrategy();
        mergeStrategy.addIgnoreMergeRow("identityTypeCollect", "identityNumberCollect");
        
        List<BackRouteDTO> list = new ArrayList<>();
        Integer headerRow = 1;
        Integer excelRowIndex = headerRow;
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < i; j++) {
                BackRouteDTO dto = new BackRouteDTO();
                dto.setObserverId(i);
                dto.setPersonCode("1");
                dto.setPersonName("2");
                dto.setIdentityTypeCollect("3");
                dto.setIdentityTypeCollectView("4");
                dto.setIdentityNumberCollect("5");
                list.add(dto);
                
                mergeStrategy.addMergeRow(new MergeStrategy.MergeRow(i, excelRowIndex, i));
                excelRowIndex++;
            }
        }
        String path = System.getProperty("user.dir") + File.separator + "target" + File.separator;
        String fileName = path + "merge" + System.currentTimeMillis() + ".xlsx";
        
        EasyExcel.write(fileName, BackRouteDTO.class)
                .registerWriteHandler(mergeStrategy)
                .sheet("模板").doWrite(list);
    }
    
    
}
