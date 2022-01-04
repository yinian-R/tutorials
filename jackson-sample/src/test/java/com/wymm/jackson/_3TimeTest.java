package com.wymm.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wymm.jackson.model.Car;
import com.wymm.jackson.time.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 序列化和反序列化日期对象
 * 引入依赖 jackson-datatype-jsr310，支持 Java 8 日期
 */
class _3TimeTest {
    
    /**
     * 使用 setDateFormat 方法处理日期格式
     * java.util.Date的默认序列化产生一个数字，即epoch时间戳。但这不是人类可读的，并且需要进一步转换以人类可读的格式显示。
     */
    @Test
    void useSetDateFormat() throws JsonProcessingException, ParseException {
        Request request = new Request();
        request.setCar(new Car("yellow", "renault"));
        request.setDatePurchased(new Date());
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        
        String jsonString = objectMapper.writeValueAsString(request);
        System.out.println(jsonString);
        // output ：{"car":{"color":"yellow","type":"renault"},"datePurchased":"2020-07-09 16:02:03"}
    }
    
    /**
     * 使用 @JsonFormat 注解以控制单个类字段，而不是全局类的日期格式
     */
    @Test
    void usingJsonFormatAnnotationToFormatDate() throws JsonProcessingException {
        Request request = new Request() {
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
            private Date datePurchased;
        };
        request.setCar(new Car("yellow", "renault"));
        request.setDatePurchased(new Date());
        
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(request);
        System.out.println(jsonString);
        // output: {"car":{"color":"yellow","type":"renault"},"datePurchased":"09-07-2020 08:44:55"}
    }
    
    
    /**
     * 使用自定义时间序列化器处理日期，使用 @JsonSerialize 控制单个类字段
     */
    @Test
    void whenUsingCustomDateSerializerAndJsonSerializeAnnotation() throws JsonProcessingException {
        Request request = new Request() {
            @JsonSerialize(using = CustomDateSerializer.class)
            private Date datePurchased;
        };
        request.setCar(new Car("yellow", "renault"));
        request.setDatePurchased(new Date());
        
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(request);
        System.out.println(jsonString);
        // output: {"car":{"color":"yellow","type":"renault"},"datePurchased":"09-07-2020 04:43:08"}
    }
    
    /**
     * 使用自定义时间序列化器处理日期，全局生效
     */
    @Test
    void whenUsingCustomDateSerializer() throws JsonProcessingException {
        Request request = new Request();
        request.setCar(new Car("yellow", "renault"));
        request.setDatePurchased(new Date());
        
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Date.class, new CustomDateSerializer());
        objectMapper.registerModule(simpleModule);
        String jsonString = objectMapper.writeValueAsString(request);
        System.out.println(jsonString);
        // output: {"car":{"color":"yellow","type":"renault"},"datePurchased":"09-07-2020 04:43:08"}
    }
    
    
    /**
     * 使用 Jackson 来序列化 Java 8 DateTime
     */
    @Test
    void whenSerializingJava8Date_thenCorrect() throws JsonProcessingException {
        LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);
        
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 自定义日期格式
        //javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(javaTimeModule);
        // 不设置这个，并且没有 @JsonFormat，日期会被序列化成时间戳数组
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        String jsonString = mapper.writeValueAsString(date);
        System.out.println(jsonString);
        assertTrue(jsonString.contains("2014-12-20T02:30:00"));
    }
    
    /**
     * 不需要额外依赖关系，使用自定义序列化器将 Java 8 LocalDateTime 写出为 JSON
     */
    @Test
    void whenSerializingJava8DateWithCustomSerializer_thenCorrect()
            throws JsonProcessingException {
        LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);
        Event event = new Event("party", date);
        
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(event);
        assertTrue(result.contains("2014-12-20 02:30:00"));
    }
    
    /**
     * 使用 Jackson @JsonDeserialize 来反序列化 Java 8 DateTime
     */
    @Test
    void whenDeserializingDateWithJackson_thenCorrect()
            throws JsonProcessingException, IOException {
        String json = "{\"name\":\"party\",\"eventDate\":\"2014-12-14T02:30:00\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Event event = objectMapper.readValue(json, Event.class);
        assertEquals("2014-12-14T02:30:00", event.getEventDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
    
    /**
     * 在保留时区的情况下序列化、反序列化Joda ZonedDateTime
     * ZonedDateTime 若不保留时区，将被转换为 UTC 世界统一时间，也就是+0时区
     */
    @Test
    void whenDeserialisingZonedDateTimeWithDefaults_thenNotCorrect()
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        
        // 序列化
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        String converted = objectMapper.writeValueAsString(now);
        
        // 反序列化
        ZonedDateTime restored = objectMapper.readValue(converted, ZonedDateTime.class);
        System.out.println("converted: " + converted);
        System.out.println("restored: " + restored);
    }
    
    /**
     * 使用自定义反序列化器，将 Date 字符串转换成 Java 对象
     */
    @Test
    void whenDeserializingDateUsingCustomDeserializer_thenCorrect()
            throws JsonProcessingException, IOException {
        String json = "{\"datePurchased\":\"20-12-2014 02:30:00\"}";
        
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new CustomDateDeserializer());
        objectMapper.registerModule(module);
        
        Request event = objectMapper.readerFor(Request.class).readValue(json);
        assertEquals("20-12-2014 02:30:00", df.format(event.getDatePurchased()));
    }
    
    /**
     * 反序列化 LocalDate
     */
    @Test
    void whenSerializingJava8DateAndReadingValue_thenCorrect() throws IOException {
        String stringDate = "\"2014-12-20\"";
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        LocalDate result = objectMapper.readValue(stringDate, LocalDate.class);
        assertTrue(result.toString().contains("2014-12-20"));
    }
    
    /**
     * POJO中的注释：
     * - @JsonDeserialize 注释用于指定自定义解串器解组JSON对象
     * - @JsonSerialize 表示在编组实体时要使用的自定义序列化程序
     * - @JsonFormat 允许我们指定将日期值序列化为的格式
     * 尽管这种方法比使用JavaTimeModule默认值需要更多的工作，但它的可定制性更高
     */
    @Test
    void whenSerializingJava8DateAndReadingFromEntity_thenCorrect() throws IOException {
        String jsonString = "{\"name\":\"party\",\"eventDate\":\"20-12-2014\"}";
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        EventWithLocalDate result = objectMapper.readValue(jsonString, EventWithLocalDate.class);
        assertTrue(result.getEventDate().toString().contains("2014-12-20"));
    }
    
}
