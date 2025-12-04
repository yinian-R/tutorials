package com.example.springwebredis.web.controller;

import com.example.springwebredis.web.model.PersonStatisticsInfo;
import com.example.springwebredis.web.model.qo.PersonStatisticsInfoQO;
import com.example.springwebredis.web.service.StatService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "spring cache condition使用示例")
@RequiredArgsConstructor
@RestController
@RequestMapping("/statCondition")
public class StatConditionController {
    
    private final StatService statService;
    
    @GetMapping("/findTotalNumByCodeAndReportDateTime")
    public List<PersonStatisticsInfo> findTotalNumByCodeAndReportDateTime(String code, LocalDateTime dateTime) {
        return statService.findTotalNumByCodeAndReportDateTime(code, dateTime);
    }
    
    @GetMapping("/findCacheByQo")
    public List<PersonStatisticsInfo> findCacheByQo(PersonStatisticsInfoQO qo) {
        return statService.findCacheByQo(qo);
    }
    
    @GetMapping("/findCacheByQoMethod")
    public List<PersonStatisticsInfo> findCacheByQoMethod(PersonStatisticsInfoQO qo) {
        return statService.findCacheByQoMethod(qo);
    }
    
    
}

