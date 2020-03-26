package com.wymm.lambda;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

class AndThen_ComposeTest {
    
    @Test
    void test() {
        // 需要根据链式调用依次获取当前时间的年、月、日、时、分、秒，逐步组合在一起
        Function<String, String> function = (s -> s + LocalDate.now().getYear());
        Function<String, String> stringStringFunction = function
                .andThen(s -> s + "-" + LocalDate.now().getMonthValue())
                .andThen(s -> s + "-" + LocalDate.now().getDayOfMonth())
                .andThen(s -> s + " " + LocalDateTime.now().getHour())
                .andThen(s -> s + ":" + LocalDateTime.now().getMinute())
                .andThen(s -> s + ":" + LocalDateTime.now().getSecond());
        
        System.out.println(stringStringFunction.apply(""));
        
        
        // 需要根据链式调用依次获取当前时间的秒、分、时、日、月、年，逐步组合在一起
        Function<String, String> function2 = (s -> s + ":" + LocalDateTime.now().getSecond());
        Function<String, String> stringStringFunction2 = function2
                .compose(s -> s + ":" + LocalDateTime.now().getMinute())
                .compose(s -> s + " " + LocalDateTime.now().getHour())
                .compose(s -> s + "-" + LocalDate.now().getDayOfMonth())
                .compose(s -> s + "-" + LocalDate.now().getMonthValue())
                .compose(s -> s + LocalDate.now().getYear());
        
        System.out.println(stringStringFunction2.apply(""));
        
        
        // 柯里化
        // 需要根据链式调用依次获取当前时间的年、月、日、时、分、秒，逐步组合在一起
        LocalDateTime localDateTime = LocalDateTime.now();
        int[] nums = {
                localDateTime.getYear(),
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                localDateTime.getHour(),
                localDateTime.getMinute(),
                localDateTime.getSecond()
        };
        Function<Object, Function<Object, Function<Object, Function<Object, Function<Object, Function<Object, String>>>>>> customFormatDate
                = y -> M -> d -> h -> m -> s -> String.format("%s-%s-%s %s:%s:%s", y, M, d, h, m, s);
        for (int i = 0; i < nums.length; i++) {
    
            if (customFormatDate instanceof Function) {
                Object obj = customFormatDate.apply(nums[i]);
                if (obj instanceof Function) {
                    customFormatDate = (Function) obj;
                } else {
                    System.out.println("格式化结果：" + obj);
                }
            }
        }
        
    }
    
}
