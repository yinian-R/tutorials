package com.wymm.springvalidsasmple;

import com.wymm.springvalidsasmple.model.Book;
import com.wymm.springvalidsasmple.util.SpElUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Slf4j
class SpElTest {
    
    @Test
    void base() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        String message = (String) exp.getValue();
        System.out.println(message);
    }
    
    @Test
    void getValue() {
        Book book = new Book();
        book.setName("人与自然");
        book.setAge(18);
        book.setBookType("D1");
        book.setBookTypeText("自然科学");
        
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'未知名称：' + #root.name");
        System.out.println(exp.getValue(book, String.class));
        
        ExpressionParser parser2 = new SpelExpressionParser();
        Expression exp2 = parser2.parseExpression("'未知名称：' + name");
        System.out.println(exp2.getValue(book, String.class));
        
        ExpressionParser parser3 = new SpelExpressionParser();
        Expression exp3 = parser3.parseExpression("{ '未知名称：' + name }");
        System.out.println(exp3.getValue(book, String.class));
    }
    
    @Test
    void message() {
        Book book = new Book();
        book.setName("人与自然");
        book.setAge(18);
        book.setBookType("D1");
        book.setBookTypeText("自然科学");
        
        String message = SpElUtils.parserMessage(book, "{ '未知名称：' + name }");
        System.out.println(message);
    }
    
}
