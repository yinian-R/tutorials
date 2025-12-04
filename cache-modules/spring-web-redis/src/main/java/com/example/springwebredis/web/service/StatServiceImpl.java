package com.example.springwebredis.web.service;

import com.example.springwebredis.web.mapper.PersonStatisticsInfoMapper;
import com.example.springwebredis.web.model.PersonStatisticsInfo;
import com.example.springwebredis.web.model.qo.PersonStatisticsInfoQO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatServiceImpl implements StatService {
    
    @Resource
    private PersonStatisticsInfoMapper personStatisticsInfoMapper;
    
    
    /**
     * 缓存，查询时间在当前时间30分钟前的
     *
     * @param code
     * @param toDateTime
     * @return
     */
    @Cacheable(value = "personStat:findTotalNumByCodeAndReportDateTime",
            condition = "{ #p1 != null and #p1.isBefore(T(java.time.LocalDateTime).now().minusMinutes(30)) }")
    @Override
    public List<PersonStatisticsInfo> findTotalNumByCodeAndReportDateTime(String code, LocalDateTime toDateTime) {
        return personStatisticsInfoMapper.findTotalNumByCodeAndReportDate(code, toDateTime);
    }
    
    
    /**
     * 缓存，使用对象的属性写 spring el 判断是否缓存
     *
     * @param qo
     * @return
     */
    @Cacheable(value = "personStat:findCacheByQo",
            condition = "{ #p0.toDateTime != null and #p0.toDateTime.isBefore(T(java.time.LocalDateTime).now().minusMinutes(30)) }")
    @Override
    public List<PersonStatisticsInfo> findCacheByQo(PersonStatisticsInfoQO qo) {
        return personStatisticsInfoMapper.findTotalNumByCodeAndReportDate(qo.getCode(), qo.getToDateTime());
    }
    
    /**
     * 缓存，使用对象的方法写 spring el 判断是否缓存
     *
     * @param qo
     * @return
     */
    @Cacheable(value = "personStat:findCacheByQoMethod",
            condition = "{ #p0.isCache(30) }")
    @Override
    public List<PersonStatisticsInfo> findCacheByQoMethod(PersonStatisticsInfoQO qo) {
        return personStatisticsInfoMapper.findTotalNumByCodeAndReportDate(qo.getCode(), qo.getToDateTime());
    }
    
    
}
