package com.wymm.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class _7CustomTimePeriodTest {
    
    @Test
    void test() {
        TimePeriod timePeriod = new TimePeriod(LocalDate.parse("2022-03-14").atStartOfDay(),
                LocalDateTime.of(LocalDate.parse("2022-03-14") , LocalTime.MAX));
        timePeriod.fill(LocalDateTime.parse("2022-03-13T00:00:00"), LocalDateTime.parse("2022-03-13T00:46:18"));
        timePeriod.fill(LocalDateTime.parse("2022-03-14T00:00:00"), LocalDateTime.parse("2022-03-14T00:46:18"));
        timePeriod.fill(LocalDateTime.parse("2022-03-14T00:00:00"), LocalDateTime.parse("2022-03-14T00:00:47"));
        timePeriod.fill(LocalDateTime.parse("2022-03-14T00:00:00"), LocalDateTime.parse("2022-03-14T01:46:18"));
        timePeriod.fill(LocalDateTime.parse("2022-03-14T03:46:18"), LocalDateTime.parse("2022-03-14T04:46:19"));
        timePeriod.fill(LocalDateTime.parse("2022-03-14T18:46:18"), LocalDateTime.parse("2022-03-15T04:46:19"));
        timePeriod.fill(LocalDateTime.parse("2022-03-13T18:46:18"), LocalDateTime.parse("2022-03-14T00:46:18"));
        timePeriod.fill(LocalDateTime.parse("2022-03-14T11:46:20"), LocalDateTime.parse("2022-03-14T12:46:20"));
        timePeriod.fill(LocalDateTime.parse("2022-03-14T12:46:21"), LocalDateTime.parse("2022-03-14T13:46:21"));
        timePeriod.fill(LocalDateTime.parse("2022-03-15T12:46:21"), LocalDateTime.parse("2022-03-15T13:46:21"));
        Set<TimePeriodItem> fillDateTimeSet = timePeriod.getFillDateTimeSet();
        Set<TimePeriodItem> notFillDateTimeSet = timePeriod.getNotFillDateTimeSet();
        List<LocalDateTime> fillDateTimeList = timePeriod.getFillDateTimeList();
        System.out.println();
    }
    
    
    
}
