package com.wymm.jackson.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 当 isHidden() 不为 true 时，把序列化交给默认序列化器
 * 重写了 isEmpty 方法，确保在 Hidable 是属性的情况下，属性名称能从 JSON 中排除
 */
public class HidableSerializer extends JsonSerializer<Hidable> {
    
    private JsonSerializer<Object> jsonSerializer;
    
    public HidableSerializer(JsonSerializer<Object> jsonSerializer) {
        this.jsonSerializer = jsonSerializer;
    }
    
    @Override
    public void serialize(Hidable value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value.isHidden()) {
            return;
        }
        jsonSerializer.serialize(value, gen, serializers);
    }
    
    @Override
    public boolean isEmpty(SerializerProvider provider, Hidable value) {
        return value == null || value.isHidden();
    }
}
