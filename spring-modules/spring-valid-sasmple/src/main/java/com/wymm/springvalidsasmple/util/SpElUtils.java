package com.wymm.springvalidsasmple.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.ObjectUtils;

/**
 * @since 2022-08-22
 */
@Slf4j
public class SpElUtils {
    
    
    /**
     * 填充 data 中的数据到表达式中
     *
     * @param data    实体对象参数
     * @param message 表达式字符串，格式：{'name is ' + name}
     * @return 填充后字符串
     */
    public static String parserMessageSafe(Object data, String message) {
        try {
            return parserMessage(data, message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return message;
        }
    }
    
    /**
     * 填充 data 中的数据到表达式中
     *
     * @param data    实体对象参数
     * @param message 表达式字符串，格式：{'name is ' + name}
     * @return 填充后字符串
     */
    public static String parserMessage(Object data, String message) {
        if (ObjectUtils.isEmpty(message)) {
            return message;
        } else {
            if (message.startsWith("{") && message.endsWith("}")) {
                ExpressionParser parser = new SpelExpressionParser();
                Expression exp = parser.parseExpression(message);
                return exp.getValue(data, String.class);
            }
            return message;
        }
    }
    
}
