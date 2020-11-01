package com.wymm.optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class _2OptionalFilterTest {
    
    /**
     * 可以使用filter方法对包装的值进行内联测试
     */
    @Test
    public void filter() {
        Integer year = 2020;
        Optional<Integer> yearOptional = Optional.of(year);
        assertTrue(yearOptional.filter(y -> y > 2019).isPresent());
        assertFalse(yearOptional.filter(y -> y < 2019).isPresent());
    }
    
    /**
     * 假设我们只关系价格
     * 没有Optional的代码
     */
    private boolean priceIsInRange1(Modem modem) {
        boolean isInRange = false;
        if (modem != null && modem.getPrice() != null
                && (modem.getPrice() >= 10
                && modem.getPrice() <= 15)) {
            isInRange = true;
        }
        return isInRange;
    }
    
    @Test
    public void whenFiltersWithoutOptional_thenCorrect() {
        assertTrue(priceIsInRange1(new Modem(10.0)));
        assertFalse(priceIsInRange1(new Modem(9.9)));
        assertFalse(priceIsInRange1(new Modem(null)));
        assertFalse(priceIsInRange1(new Modem(15.5)));
        assertFalse(priceIsInRange1(null));
    }
    
    /**
     * 假设我们只关系价格
     * 使用Optional的代码
     */
    private boolean priceIsInRange2(Modem modem) {
        return Optional.ofNullable(modem)
                .map(Modem::getPrice)
                .filter(price -> price > 10)
                .filter(price -> price < 15)
                .isPresent();
    }
    
    @Test
    public void whenFiltersWithOptional_thenCorrect() {
        assertTrue(priceIsInRange1(new Modem(10.0)));
        assertFalse(priceIsInRange1(new Modem(9.9)));
        assertFalse(priceIsInRange1(new Modem(null)));
        assertFalse(priceIsInRange1(new Modem(15.5)));
        assertFalse(priceIsInRange1(null));
    }
    
    @Data
    @AllArgsConstructor
    private class Modem {
        private Double price;
    }
}
