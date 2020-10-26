package com.wymm.greeter.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.util.ClassUtils;

import static org.junit.jupiter.api.Assertions.*;

class GreeterBeanFactoryPostProcessorTest {
    
    @Test
    public void test() {
        boolean hasClass = ClassUtils.isPresent("com.wymm.greeter.library.GreeterTemplate",
                GreeterBeanFactoryPostProcessor.class.getClassLoader());
        assertTrue(hasClass);
    }
}