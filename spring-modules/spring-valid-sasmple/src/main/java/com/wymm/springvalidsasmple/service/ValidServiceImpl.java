package com.wymm.springvalidsasmple.service;

import com.wymm.springvalidsasmple.model.Book;
import com.wymm.springvalidsasmple.util.SpElUtils;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @since 2022-08-22
 */
@Slf4j
@AllArgsConstructor
@Service
public class ValidServiceImpl implements ValidService {
    
    private final Validator validator;
    
    @Override
    public String uploadBook(List<Book> list) {
        for (Book book : list) {
            checkArgs(book, Default.class);
            checkArgs(book, Book.DictGroup.class);
        }
        
        return null;
    }
    
    
    private void checkArgs(Book book, Class<?> clazz) {
        Set<ConstraintViolation<Book>> violations = validator.validate(book, clazz);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Book> constraintViolation : violations) {
                String message = constraintViolation.getMessage();
                message = SpElUtils.parserMessage(book, message);
                sb.append(message);
            }
            //throw new ConstraintViolationException("Error occurred: " + sb, violations);
            log.warn(sb.toString());
        }
    }
}
