package com.wymm.fragment;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class _5ValidTest {
    
    private static Validator VALIDATOR;
    
    private static Validator getValidator() {
        if (VALIDATOR == null) {
            VALIDATOR = Validation.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();
        }
        return VALIDATOR;
    }
    
    public static <T> Set<ConstraintViolation<T>> validateBean(T data, Class<?>... groups) {
        return getValidator().validate(data, groups);
    }
    
    /**
     * 使用泛型实现通用编码校验 JSR-303
     */
    @Test
    public void validate() {
        User user = new User();
        
        Set<ConstraintViolation<User>> constraintViolations = validateBean(user);
        constraintViolations.forEach(item -> System.out.println(item.getMessage()));
    }
    
    @Data
    public static class User {
        private String id;
        @NotBlank(message = "姓名不能为空")
        private String userName;
        private List<Long> ids;
    }
}
