package com.wymm.jackson.time;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义序列化器将Java 8 DateTime写出为JSON
 */
public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private DateTimeFormatter _formatter;
    
    public CustomLocalDateTimeSerializer() {
        this(formatter);
    }
    
    public CustomLocalDateTimeSerializer(DateTimeFormatter formatter) {
        this(LocalDateTime.class, formatter);
    }
    
    public CustomLocalDateTimeSerializer(Class<LocalDateTime> t, DateTimeFormatter formatter) {
        super(t);
        this._formatter = formatter;
    }
    
    @Override
    public void serialize(
            LocalDateTime value,
            JsonGenerator gen,
            SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        
        gen.writeString(_formatter.format(value));
    }
}