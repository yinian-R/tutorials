package com.wymm.date;

import java.time.LocalDateTime;

public class TimeUtils {
    
    /**
     * v1 <= v2  true
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isBeforeOrEqual(LocalDateTime v1, LocalDateTime v2) {
        return v1.isBefore(v2) || v1.isEqual(v2);
    }
    
    /**
     * v1 >= v2  true
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isAfterOrEqual(LocalDateTime v1, LocalDateTime v2) {
        return v1.isAfter(v2) || v1.isEqual(v2);
    }
    
}
