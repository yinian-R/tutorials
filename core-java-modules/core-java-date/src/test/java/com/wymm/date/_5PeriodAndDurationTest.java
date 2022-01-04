package com.wymm.date;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * 日期类、时间类
 * Period 类使用年、月、日来表示一段时间
 * Duration 类表示以秒或者纳秒为单位的时间间隔，它最适合处理较短的时间间隔(在需要更高精度的情况下)
 */
@Slf4j
class _5PeriodAndDurationTest {
    
    @Test
    void period() {
        LocalDate startDate = LocalDate.of(2015, 2, 20);
        LocalDate endDate = LocalDate.of(2017, 1, 15);
        
        // 我们可以使用 between() 获取两个日期之间的差
        Period period = Period.between(startDate, endDate);
        // 我们可以使用getYears()，getMonths()，getDays()方法确定期间的日期单位
        log.info("Years:" + period.getYears() +
                " months:" + period.getMonths() +
                " days:" + period.getDays());
        
        // 判断 endDate 是否早于 startDate
        assertFalse(period.isNegative());
        
        // 创建 Period 的另一种方法就是使用专用方法，基于天、月、周或年
        Period fromUnits = Period.of(3, 10, 10);
        Period fromDays = Period.ofDays(50);
        Period fromMonths = Period.ofMonths(5);
        Period fromYears = Period.ofYears(10);
        Period fromWeeks = Period.ofWeeks(40);
        // 基于周的，在存入时会转换为天，也就是周*7
        assertEquals(280, fromWeeks.getDays());
        
        
        // 可以通过分析文本创建 Period 对象，该文本序列的格式必须为”PnYnMnD“：
        Period fromCharYears = Period.parse("P2Y");
        assertEquals(2, fromCharYears.getYears());
        
        Period fromCharUnits = Period.parse("P2Y3M5D");
        assertEquals(5, fromCharUnits.getDays());
        
        // 可以使用plusX()和minusX()形式的方法来增加或减少期间的值，其中X表示日期单位
        assertEquals(76, period.plusDays(50).getDays());
        assertEquals(8, period.minusMonths(2).getMonths());
    }
    
    @Test
    void duration() {
        Instant start = Instant.parse("2017-10-03T10:15:30.00Z");
        Instant end = Instant.parse("2017-10-03T10:16:30.00Z");
        // 我们可以使用 between() 将两个 Instant 之间的差异确定为 Duration
        Duration duration = Duration.between(start, end);
        
        // 我们可以使用getSeconds()或getNanoseconds()方法来确定时间单位的值
        assertEquals(60, duration.getSeconds());
        
        // 判断 end 是否早于 start
        assertFalse(duration.isNegative());
        
        // 我们还可以使用 Days()，ofHours()，ofMillis()，ofMinutes()，ofNanos()，ofSeconds() 的方法，基于多个时间单位获得 Duration 对象
        Duration fromDays = Duration.ofDays(1);
        assertEquals(86400, fromDays.getSeconds());
        Duration fromMinutes = Duration.ofMinutes(60);
        
        // 使用文本创建 Duration 对象，其格式是“PnDTnHnMn.nS”
        Duration fromChar1 = Duration.parse("P1DT1H10M10.5S");
        // 十分钟
        Duration fromChar2 = Duration.parse("PT10M");
        assertEquals(10 * 60, fromChar2.getSeconds());
    
        // 可以使用toDays()，toHours()，toMillis()，toMinutes()将持续时间转换为其他时间单位
        assertEquals(1, fromMinutes.toHours());
    
        // 可以使用plusX（）或minusX（）形式的方法来增加或减少持续时间值，其中X可以代表几天，几小时，几毫秒，几分钟，毫微秒或几秒钟
        assertEquals(120, duration.plusSeconds(60).getSeconds());
        assertEquals(30, duration.minusSeconds(30).getSeconds());
        // 我们还可以将plus（）和minus（）方法与指定TemporalUnit的参数一起使用来进行加法或减法
        assertEquals(120, duration.plus(60, ChronoUnit.SECONDS).getSeconds());
        assertEquals(30, duration.minus(30, ChronoUnit.SECONDS).getSeconds());
    }
}
