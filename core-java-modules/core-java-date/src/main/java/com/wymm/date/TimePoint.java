package com.wymm.date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimePoint {
    private Integer start;
    private Integer end;
}