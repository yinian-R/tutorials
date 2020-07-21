package com.wymm.jackson._enum;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class DistanceSerializer extends StdSerializer<Distance4> {
    
    public DistanceSerializer() {
        super(Distance4.class);
    }
    
    public DistanceSerializer(Class t) {
        super(t);
    }
    
    
    @Override
    public void serialize(Distance4 distance, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeFieldName("name");
        generator.writeString(distance.name());
        generator.writeFieldName("unit");
        generator.writeString(distance.getUnit());
        generator.writeFieldName("meters");
        generator.writeNumber(distance.getMeters());
        generator.writeEndObject();
    }
}
