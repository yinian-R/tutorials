package com.wymm.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.wymm.jackson.filter.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 仅在满足特定的自定义条件的情况下序列化字段
 * 以下演示的高级过滤功能非常强大，并且在使用 Jackson 序列化复杂对象时，可以非常灵活地自定义 JSON。
 * 一个更灵活但也更复杂的替代方法是完全使用自定义序列化器来控制 JSON 输出，因此，如果此解决方法不够灵活，那么值得研究一下。
 */
class _8FilterTest {
    
    /**
     * 自定义过滤器控制序列化控制
     * 实现当 intValue 属性大于0时才进行序列化此属性
     */
    @Test
    void whenSerialize_thenControlProcess_thenCorrect() throws JsonProcessingException {
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = new SimpleBeanPropertyFilter() {
            @Override
            public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
                if (include(writer)) {
                    if (writer.getName().equals("intValue")) {
                        int intValue = ((MyDto) pojo).getIntValue();
                        if (intValue > 0) {
                            writer.serializeAsField(pojo, jgen, provider);
                        }
                    } else {
                        writer.serializeAsField(pojo, jgen, provider);
                    }
                } else if (!jgen.canOmitFields()) { // since 2.3
                    writer.serializeAsOmittedField(pojo, jgen, provider);
                }
            }
        };
        
        FilterProvider myFilter = new SimpleFilterProvider().addFilter("myFilter", simpleBeanPropertyFilter);
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setFilterProvider(myFilter);
        MyDto myDto = new MyDto();
        myDto.setIntValue(-1);
        String jsonString = objectMapper.writeValueAsString(myDto);
        assertFalse(jsonString.contains("intValue"));
    }
    
    /**
     * 基于属性值进行序列化时跳过对象。
     * 跳过 hidden 为 true 的对象
     */
    @Test
    void whenHidden_thenCorrect() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.registerModule(new SimpleModule() {
            public void setupModule(SetupContext context) {
                super.setupModule(context);
                context.addBeanSerializerModifier(new BeanSerializerModifier() {
                    @Override
                    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
                        if (Hidable.class.isAssignableFrom(beanDesc.getBeanClass())) {
                            return new HidableSerializer((JsonSerializer<Object>) serializer);
                        }
                        return serializer;
                    }
                });
            }
        });
        
        
        Address ad1 = new Address("tokyo", "jp", true);
        Address ad2 = new Address("london", "uk", false);
        Address ad3 = new Address("ny", "usa", false);
        Person p1 = new Person("john", ad1, false);
        Person p2 = new Person("tom", ad2, true);
        Person p3 = new Person("adam", ad3, false);
        
        System.out.println(objectMapper.writeValueAsString(Arrays.asList(p1, p2, p3)));
        // output: [{"name":"john"},{"name":"adam","address":{"city":"ny","country":"usa"}}]
    }
    
    /**
     * Annotation Introspector 是最强大忽略超类型属性的方法
     */
    @Test
    void whenUsingAnnotationIntrospector_thenCorrect() throws JsonProcessingException {
        Car car = new Car("Mercedes-Benz", "S500", 5, 250.0);
        Truck truck = new Truck("Isuzu", "NQR", 7500.0);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car);
        vehicles.add(truck);
        
        Fleet serializedFleet = new Fleet();
        serializedFleet.setVehicles(vehicles);
        
        
        class IgnoranceIntrospector extends JacksonAnnotationIntrospector {
            /**
             * 过滤 Vehicle 类的 model 属性
             * 过滤 Car 类的属性
             * 支持默认过滤规则
             */
            @Override
            public boolean hasIgnoreMarker(AnnotatedMember m) {
                return m.getDeclaringClass() == Vehicle.class && m.getName().equals("model")
                        || m.getDeclaringClass() == Car.class
                        || super.hasIgnoreMarker(m);
            }
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setAnnotationIntrospector(new IgnoranceIntrospector());
        String jsonDataString = objectMapper.writeValueAsString(serializedFleet);
        
        assertTrue(jsonDataString.contains("make"));
        assertTrue(jsonDataString.contains("payloadCapacity"));
        assertFalse(jsonDataString.contains("model"));
        assertFalse(jsonDataString.contains("seatingCapacity"));
        assertFalse(jsonDataString.contains("topSpeed"));
        // output: {"vehicles":[{"make":"Mercedes-Benz"},{"make":"Isuzu","payloadCapacity":7500.0}]}
    }
}
