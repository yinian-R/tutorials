package com.wymm.easyexcelsample;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wymm.easyexcelsample.excel.merger.BackRouteDTO;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MergerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    
    
    @Test
    void merger() throws JsonProcessingException {
        List<BackRouteDTO> list = new ArrayList<>();
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
            }
        }
        String path = System.getProperty("user.dir") + File.separator + "target" + File.separator;
        String fileName = path + "write" + System.currentTimeMillis() + ".xlsx";
    
    
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        
        EasyExcel.write(fileName, BackRouteDTO.class)
                .registerWriteHandler(loopMergeStrategy)
                .sheet("模板").doWrite(list);
    }
    
    
    
    
}
