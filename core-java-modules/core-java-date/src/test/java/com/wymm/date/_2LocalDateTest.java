package com.wymm.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * LocalDate在IOS下是yyyy-MM-dd格式不带具体时间的日期
 */
class _2LocalDateTest {
    
    /**
     * 获取两个日期之间的所有日期
     *
     * @param startDate 开始日期，包含
     * @param endDate   结束日期，包含
     */
    public static List<LocalDate> getDatesBetweenUsingJava8(LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween + 1)
                .mapToObj(startDate::plusDays)
                .collect(Collectors.toList());
    }
    
    @Test
    void test() {
        System.out.println("\n系统当前日期：" +
                LocalDate.now()
        );
        
        // 特定年、月和日可以使用 of 方法
        System.out.println("\n特定时间：" +
                LocalDate.of(2019, 8, 28)
        );
        
        // yyyy-MM-dd 格式的字符串可以使用 parse 方法
        System.out.println("\nyyyy-MM-dd 格式的字符串的日期：" +
                LocalDate.parse("2019-08-28")
        );
        
        System.out.println("\n添加一天日期：" +
                LocalDate.now().plusDays(1)
        );
        System.out.println("添加一月日期：" +
                LocalDate.now().plusMonths(1)
        );
        System.out.println("添加一年日期：" +
                LocalDate.now().plusYears(1)
        );
        System.out.println("添加一天日期（使用枚举）：" +
                LocalDate.now().plus(1, ChronoUnit.DAYS)
        );
        
        
        System.out.println("\n减一天日期：" +
                LocalDate.now().minusDays(1));
        System.out.println("减一月日期：" +
                LocalDate.now().minusMonths(1));
        System.out.println("减一年日期：" +
                LocalDate.now().minusYears(1));
        System.out.println("减一年日期（使用枚举）：" +
                LocalDate.now().minus(1, ChronoUnit.DAYS)
        );
        
        System.out.println("\n获取星期几：" +
                LocalDate.now().getDayOfWeek()
        );
        System.out.println("获取月中的某天：" +
                LocalDate.now().getDayOfMonth()
        );
        
        //  测试一个日期是不是闰年
        System.out.println("\n是否是闰年：" +
                LocalDate.now().isLeapYear());
        
        // 判断日期先后（之前）
        System.out.println("\n判断日期先后（之前）：" +
                LocalDate.of(2019, 7, 28).isBefore(LocalDate.of(2019, 8, 28))
        );
        // 判断日期先后（之后）
        System.out.println("判断日期先后（之后）：" +
                LocalDate.of(2019, 8, 28).isAfter(LocalDate.of(2019, 7, 28))
        );
        
        // 获取给定日期的一天的开始（2019-08-28T00:00）
        System.out.println("给定日期的一天的开始：" +
                LocalDate.parse("2019-08-28").atStartOfDay()
        );
        
        // 获取相对的日期（月初、年初……）
        System.out.println("月初：" +
                LocalDate.parse("2019-08-28").with(TemporalAdjusters.firstDayOfMonth())
        );
        System.out.println("年初：" +
                LocalDate.parse("2019-08-28").with(TemporalAdjusters.firstDayOfYear())
        );
    
        // 获取日期是本月的第几周
        int weekNumber = LocalDate.now().get(WeekFields.ISO.weekOfMonth());
        System.out.println(weekNumber);
        
        // LocalDate 转换 LocalDateTime
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
    }
    
    /**
     * 获取两个日期之间的所有日期
     */
    @Test
    void whenUsingGetDatesBetweenUsingJava8_thenCorrect() {
        LocalDate startDate = LocalDate.of(2020, 7, 1);
        LocalDate endDate = LocalDate.of(2020, 7, 10);
        List<LocalDate> dates = getDatesBetweenUsingJava8(startDate, endDate);
        System.out.println(dates);
    }
    
    @Test
    void week(){
        System.out.println("\n获取星期几：" +
                LocalDate.now().getDayOfWeek()
        );
        
        
        // 周一、周二、周三，see DayOfWeek
        String week = "MONDAY,TUESDAY,WEDNESDAY";
    
        for (String dayOfWeek : week.split(",")) {
            if (LocalDate.now().getDayOfWeek().toString().equals(dayOfWeek)) {
                System.out.println("符合" + dayOfWeek);
                
                // todo something
            } else {
                System.out.println("不符合"+dayOfWeek);
            }
        }
    }
}