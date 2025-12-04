package com.example.springwebredis.web.service;

import com.example.springwebredis.web.model.PersonStatisticsInfo;
import com.example.springwebredis.web.model.qo.PersonStatisticsInfoQO;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    
    List<PersonStatisticsInfo> findTotalNumByCodeAndReportDateTime(String code, LocalDateTime activeDateTime);
    
    List<PersonStatisticsInfo> findCacheByQo(PersonStatisticsInfoQO qo);
    
    List<PersonStatisticsInfo> findCacheByQoMethod(PersonStatisticsInfoQO qo);
    
}
