package com.wymm.date;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimePeriodItem {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
