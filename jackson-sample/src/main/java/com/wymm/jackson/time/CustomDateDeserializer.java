package com.wymm.jackson.time;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义反序列化器，将 Date 字符串转换成 Java对象
 */
public class CustomDateDeserializer extends StdDeserializer<Date> {
    
    private static SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    
    private SimpleDateFormat formatter;
    
    public CustomDateDeserializer() {
        this(Date.class, FORMATTER);
    }
    
    public CustomDateDeserializer(Class<?> vc, SimpleDateFormat formatter) {
        super(vc);
        this.formatter = formatter;
    }
    
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String text = p.getText();
        try {
            return formatter.parse(text);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
}
