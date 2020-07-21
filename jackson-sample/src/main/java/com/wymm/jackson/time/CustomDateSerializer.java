package com.wymm.jackson.time;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日期序列化器
 */
public class CustomDateSerializer extends StdSerializer<Date> {
    
    private static SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    
    private SimpleDateFormat formatter;
    
    public CustomDateSerializer() {
        this(Date.class, FORMATTER);
    }
    
    public CustomDateSerializer(Class<Date> t, SimpleDateFormat formatter) {
        super(t);
        this.formatter = formatter;
    }
    
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
}