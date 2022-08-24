package com.wymm.date;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimePeriodConvertor {
    /**
     * 日期时间转换为填充点位
     *
     * @param origin 作为原点的日期时间
     * @param value  日期时间
     * @return 填充点位
     */
    public static Integer getPoint(LocalDateTime origin, LocalDateTime value) {
        Duration between = Duration.between(origin, value);
        return Math.toIntExact(between.getSeconds());
    }
    
    /**
     * 填充点位转换为日期时间
     *
     * @param origin 作为原点的日期时间
     * @param point  填充点位
     * @return 日期时间
     */
    public static LocalDateTime pointToLocalDateTime(LocalDateTime origin, Integer point) {
        return origin.plusSeconds(point);
    }
}
