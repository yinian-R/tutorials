package com.wymm.jackson.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    
    private String color;
    private String type;
    
    /**
     * 自定义 Jackson 序列化器
     */
    public static class CustomCarSerializer extends StdSerializer<Car> {
        
        public CustomCarSerializer() {
            this(Car.class);
        }
        
        public CustomCarSerializer(Class<Car> t) {
            super(t);
        }
        
        @Override
        public void serialize(Car value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("car_brand", value.getType());
            gen.writeEndObject();
        }
        
    }
    
    /**
     * 自定义 Jackson 反序列化器
     */
    public static class CustomCarDeserializer extends StdDeserializer<Car> {
        
        public CustomCarDeserializer() {
            this(Car.class);
        }
        
        public CustomCarDeserializer(Class<?> vc) {
            super(vc);
        }
        
        @Override
        public Car deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Car car = new Car();
            
            ObjectCodec codec = p.getCodec();
            JsonNode jsonNode = codec.readTree(p);
            
            // try catch block
            JsonNode colorNode = jsonNode.get("color");
            car.setColor(colorNode.asText());
            return car;
        }
    }
}
