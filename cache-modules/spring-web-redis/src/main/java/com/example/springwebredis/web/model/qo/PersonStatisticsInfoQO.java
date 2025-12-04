package com.example.springwebredis.web.model.qo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonStatisticsInfoQO {
    
    private String code;
    
    private LocalDateTime toDateTime;
    
    
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public boolean isCache(int minutes){
        return toDateTime != null && toDateTime.isBefore(LocalDateTime.now().minusMinutes(minutes));
    }
    
}
