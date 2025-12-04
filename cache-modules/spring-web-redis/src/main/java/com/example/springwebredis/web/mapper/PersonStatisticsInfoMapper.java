package com.example.springwebredis.web.mapper;

import com.example.springwebredis.web.model.PersonStatisticsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PersonStatisticsInfoMapper {
    
    @Select("select * from person_statistics_info where code = #{code} and report_date < #{dateTime}" )
    List<PersonStatisticsInfo> findTotalNumByCodeAndReportDate(String code, LocalDateTime dateTime);
    
}
