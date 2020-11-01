package com.wymm.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.wymm.jackson.annotation.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 探究 Jackson 注解
 */
class _4AnnotationTest {
    
    
    /**
     * 序列化时，使用 @JsonAnyGetter 把 Map 中的所有键值作为标准的普通属性获取
     */
    @Test
    void whenSerializingUsingJsonAnyGetter_thenCorrect()
            throws JsonProcessingException {
        ExtendableBean extendableBean = new ExtendableBean();
        extendableBean.name = "bean";
        extendableBean.add("attr1", "value1");
        extendableBean.add("attr2", "value2");
        
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(extendableBean);
        
        assertTrue(jsonString.contains("attr1"));
        assertTrue(jsonString.contains("value1"));
        // output: {"name":"bean","attr2":"value2","attr1":"value1"}
    }
    
    /**
     * 序列化时，使用 @JsonGetter 自定义序列化的 key
     */
    @Test
    void whenSerializingUsingJsonGetter_thenCorrect()
            throws JsonProcessingException {
        MyBean bean = new MyBean(1, "My bean");
        
        String result = new ObjectMapper().writeValueAsString(bean);
        
        assertTrue(result.contains("id"));
        assertTrue(result.contains("name"));
        // output: {"id":1,"Name":"My bean"}
    }
    
    /**
     * 使用 @JsonPropertyOrder 指定序列化属性的排序
     */
    @Test
    void whenSerializingUsingJsonPropertyOrder_thenCorrect()
            throws JsonProcessingException {
        MyBean2 bean = new MyBean2(1, "My bean");
        String result = new ObjectMapper().writeValueAsString(bean);
        
        System.out.println(result);
        // output : {"name":"My bean","id":1}
    }
    
    /**
     * 使用 @JsonPropertyOrder（alphabetic = true） 指定序列化时，按字母顺序对属性进行排序
     */
    @Test
    void whenSerializingUsingJsonPropertyOrderAndAlphabetic_thenCorrect()
            throws JsonProcessingException {
        MyBean3 bean = new MyBean3(1, "My bean");
        String result = new ObjectMapper().writeValueAsString(bean);
        
        System.out.println(result);
        // output : {"id":1,"name":"My bean"}
    }
    
    /**
     * 序列化时，使用 @JsonRawValue 指定属性作为 json 字符串的值
     */
    @Test
    void whenSerializingUsingJsonRawValue_thenCorrect()
            throws JsonProcessingException {
        RawBean bean = new RawBean("My bean", "{\"attr\":false}");
        String result = new ObjectMapper().writeValueAsString(bean);
        
        assertTrue(result.contains("My bean"));
        assertTrue(result.contains("{\"attr\":false}"));
        // output : {"name":"My bean","json":{"attr":false}}
        // not use annotation output: {"name":"My bean","json":"{\"attr\":false}"}
    }
    
    /**
     * 使用 @JsonValue 指定用于序列化整个实例的方法
     */
    @Test
    void whenSerializingUsingJsonValue_thenCorrect()
            throws JsonParseException, IOException {
        String enumAsString = new ObjectMapper().writeValueAsString(TypeEnumWithValue.TYPE1);
        
        assertEquals(enumAsString, "\"Type A\"");
        // output: "Type A"
    }
    
    /**
     * 使用 @JsonRootName 指定此潜在包装实体的名称
     * 启用 SerializationFeature.WRAP_ROOT_VALUE 后，默认情况下，包装器的名称将为类的名称
     */
    @Test
    void whenSerializingUsingJsonRootName_thenCorrect()
            throws JsonProcessingException {
        UserWithRoot user = new UserWithRoot(1, "John");
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String result = mapper.writeValueAsString(user);
        
        assertTrue(result.contains("user"));
        // {"user":{"id":1,"name":"John"}}
    }
    
    /**
     * 使用 @JsonSerialize 指定使用的自定义序列化器
     * 可查看 {@link _3TimeTest#whenUsingCustomDateSerializerAndJsonSerializeAnnotation}
     *
     * 使用 @JsonDeserialize 指定使用自定义反序列化器
     * 可查看 {@link _3TimeTest#whenDeserializingDateWithJackson_thenCorrect}
     */
    
    /**
     * 使用 @JsonCreator 来调整反序列化中使用的构造函数/工厂
     * 当我们需要反序列化一些与实体不完全匹配的 JSON 时，这非常有用。
     * 使用 @JsonCreator 注释构造函数并同时使用@JsonProperty注释参数
     * <p>
     * 单个类中使用多个 @JsonCreator，只有最后定义的生效
     */
    @Test
    void whenDeserializingUsingJsonCreator_thenCorrect()
            throws IOException {
        String json = "{\"id\":1,\"theName\":\"My bean\"}";
        
        BeanWithCreator bean = new ObjectMapper()
                .readerFor(BeanWithCreator.class)
                .readValue(json);
        assertEquals("My bean", bean.name);
    }
    
    /**
     * 使用 @JsonAnySetter 使我们可以灵活地使用 Map 作为标准属性。
     * 反序列化时，JSON 的除实体对应的属性将被简单地添加到 Map 中
     */
    @Test
    void whenDeserializingUsingJsonAnySetter_thenCorrect()
            throws IOException {
        String json = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";
        
        ExtendableBean bean = new ObjectMapper()
                .readerFor(ExtendableBean.class)
                .readValue(json);
        
        assertEquals("My bean", bean.name);
        assertEquals("val2", bean.getProperties().get("attr2"));
    }
    
    /**
     * 注解 @JsonSetter 是一种替代 @JsonProperty - 将标记的方法作为setter方法
     * 当我们需要读取 JSON 数据但目标实体类与该数据不完全匹配时，我们需要调整过程以使其适合，这非常有用
     */
    @Test
    void whenDeserializingUsingJsonSetter_thenCorrect()
            throws IOException {
        String json = "{\"id\":1,\"name\":\"My bean\"}";
        
        MyBean bean = new ObjectMapper()
                .readerFor(MyBean.class)
                .readValue(json);
        assertEquals("My bean", bean.getTheName());
    }
    
    /**
     * 使用 @JsonAlias 定义反序列化时属性一个或多个替代名称
     * <p>
     * 如果 JSON 字符串中包含属性多个替代名称的，最后属性会被赋值为最后一个替代名称对应的值
     */
    @Test
    void whenDeserializingUsingJsonAlias_thenCorrect()
            throws IOException {
        String json = "{\"fName\": \"John\", \"lastName\": \"Green\"}";
        AliasBean aliasBean = new ObjectMapper().readerFor(AliasBean.class).readValue(json);
        assertEquals("John", aliasBean.getFirstName());
    }
    
    /**
     * - @JsonIgnoreProperties 是一个类级别的注释，用于标记 Jackson 将忽略的一个属性或属性列表
     */
    @Test
    void whenSerializingUsingJsonIgnoreProperties_thenCorrect()
            throws JsonProcessingException {
        BeanWithIgnore bean = new BeanWithIgnore(1, "My bean");
        
        String result = new ObjectMapper().writeValueAsString(bean);
        assertTrue(result.contains("My bean"));
        assertFalse(result.contains(("id")));
        // output: {"name":"My bean"}
        
        BeanWithIgnore beanWithIgnore = new ObjectMapper().readValue("{\"id\":10,\"name\":\"My bean\"}", BeanWithIgnore.class);
        assertEquals(beanWithIgnore.id, 0);
    }
    
    /**
     * - @JsonIgnore 注释用来在字段级别标记被忽略的属性
     */
    @Test
    void whenSerializingUsingJsonIgnore_thenCorrect()
            throws JsonProcessingException {
        
        BeanWithIgnore2 bean = new BeanWithIgnore2(1, "My bean");
        
        String result = new ObjectMapper().writeValueAsString(bean);
        
        assertTrue(result.contains("My bean"));
        assertFalse(result.contains(("id")));
    }
    
    /**
     * - @JsonIgnoreType 将带注释的类型的所有属性标记为忽略
     */
    @Test
    void whenSerializingUsingJsonIgnoreType_thenCorrect()
            throws JsonProcessingException, ParseException {
        
        User.Name name = new User.Name("John", "Doe");
        User user = new User(1, name);
        
        String result = new ObjectMapper()
                .writeValueAsString(user);
        
        System.out.println(result);
        assertTrue(result.contains("1"));
        assertFalse(result.contains("name"));
        assertFalse(result.contains("John"));
    }
    
    /**
     * 使用 @JsonInclude 排除具有null/空/默认值等属性
     */
    @Test
    void whenSerializingUsingJsonInclude_thenCorrect()
            throws JsonProcessingException {
        
        JsonIncludeBean bean = new JsonIncludeBean(1, null);
        
        String result = new ObjectMapper().writeValueAsString(bean);
        
        assertTrue(result.contains("1"));
        assertFalse(result.contains("name"));
    }
    
    /**
     * - @JsonAutoDetect 可以覆盖默认语义，即哪些属性可见，哪些属性不可见
     * <p>
     * 让我们来看一个简单的示例，该批注如何非常有用–让我们序列化私有属性:
     */
    @Test
    void whenSerializingUsingJsonAutoDetect_thenCorrect()
            throws JsonProcessingException {
        
        PrivateBean bean = new PrivateBean(1, "My bean");
        
        String result = new ObjectMapper()
                .writeValueAsString(bean);
        
        assertTrue(result.contains("1"));
        assertTrue(result.contains("My bean"));
    }
    
    /**
     * Jackson多态类型处理注释：
     * - @JsonTypeInfo – 指示要在序列化中包含哪些类型信息的详细信息
     * - @JsonTypeName – 定义用于注释类的逻辑类型名称
     * <p>
     * 序列化多态类型
     */
    @Test
    void whenSerializingPolymorphic_thenCorrect()
            throws JsonProcessingException {
        Zoo.Dog dog = new Zoo.Dog("lacy");
        Zoo zoo = new Zoo(dog);
        
        String result = new ObjectMapper().writeValueAsString(zoo);
        
        assertTrue(result.contains("type"));
        assertTrue(result.contains("dog"));
        // output: {"animal":{"$type":"dog","name":"lacy","barkVolume":0.0}}
    }
    
    /**
     * Jackson多态类型处理注释
     * - @JsonSubTypes – 指示带注释类型的子类型
     * <p>
     * 反序列化多态类型
     */
    @Test
    void whenDeserializingPolymorphic_thenCorrect()
            throws IOException {
        String json = "{\"animal\":{\"name\":\"lacy\",\"$type\":\"cat\"}}";
        
        Zoo zoo = new ObjectMapper()
                .readerFor(Zoo.class)
                .readValue(json);
        
        assertEquals("lacy", zoo.animal.name);
        assertEquals(Zoo.Cat.class, zoo.animal.getClass());
    }
    
    /**
     * - @JsonProperty注释，以表明在JSON属性名
     * 在处理非标准的getter和setter时，让我们使用@JsonProperty对属性名称进行序列化/反序列化
     */
    @Test
    void whenUsingJsonProperty_thenCorrect()
            throws IOException {
        JsonPropertyBean bean = new JsonPropertyBean(1, "My bean");
        
        String result = new ObjectMapper().writeValueAsString(bean);
        
        assertTrue(result.contains("My bean"));
        assertTrue(result.contains("1"));
        
        JsonPropertyBean resultBean = new ObjectMapper()
                .readerFor(JsonPropertyBean.class)
                .readValue(result);
        assertEquals("My bean", resultBean.getTheName());
    }
    
    /**
     * - @JsonUnwrapped定义在序列化/反序列化时应该解包/展平的值
     */
    @Test
    void whenSerializingUsingJsonUnwrapped_thenCorrect()
            throws JsonProcessingException, ParseException {
        UnwrappedUser.Name name = new UnwrappedUser.Name("John", "Doe");
        UnwrappedUser user = new UnwrappedUser(1, name);
        
        String result = new ObjectMapper().writeValueAsString(user);
        
        assertTrue(result.contains("John"));
        assertFalse(result.contains("name"));
        // output: {"id":1,"firstName":"John","lastName":"Doe"}
    }
    
    /**
     * - @JsonView 将在其中包含属性以进行序列化/反序列化的视图
     * 在使用 writerWithView 时，没有设置 @JsonView 的也能序列化
     */
    @Test
    void whenSerializingUsingJsonView_thenCorrect()
            throws JsonProcessingException {
        Views.Item item = new Views.Item(2, "book", "John");
        
        String result = new ObjectMapper()
                .writerWithView(Views.Public.class)
                .writeValueAsString(item);
        
        assertTrue(result.contains("book"));
        assertTrue(result.contains(("2")));
        assertFalse(result.contains(("John")));
        
        
        String json = "{\"id\":1,\"itemName\":\"book\",\"ownerName\":\"John\"}";
        Views.Item o = new ObjectMapper()
                .readerWithView(Views.Public.class)
                .forType(Views.Item.class)
                .readValue(json);
        assertEquals(o.id, 1);
        assertEquals(o.itemName, "book");
        assertNull(o.ownerName);
    }
    
    /**
     * 处理字段属性间双向连接
     * - @JsonManagedReference 和 @JsonBackReference 注释可以处理父/子关系并在循环中工作
     * 不序列化使用 @JsonManagedReference 注解的类对象中使用 @JsonBackReference 注解的属性
     */
    @Test
    void whenSerializingUsingJacksonReferenceAnnotation_thenCorrect()
            throws JsonProcessingException {
        UserWithRef userWithRef = new UserWithRef(1, "John");
        ItemWithRef itemWithRef = new ItemWithRef(2, "book", userWithRef);
        userWithRef.addItem(itemWithRef);
        
        String result = new ObjectMapper().writeValueAsString(itemWithRef);
        
        assertTrue(result.contains("book"));
        assertTrue(result.contains("John"));
        assertFalse(result.contains("userItems"));
        // output: {"id":2,"itemName":"book","owner":{"id":1,"name":"John"}}
    }
    
    /**
     * - @JsonIdentityInfo 指示在对值进行序列化/反序列化时应使用对象标识
     * 例如，用于处理无限递归类型的问题。
     */
    @Test
    void whenSerializingUsingJsonIdentityInfo_thenCorrect()
            throws JsonProcessingException {
        UserWithIdentity user = new UserWithIdentity(1, "John");
        ItemWithIdentity item = new ItemWithIdentity(3, "book", user);
        user.addItem(item);
        
        String result = new ObjectMapper().writeValueAsString(item);
        
        assertTrue(result.contains("book"));
        assertTrue(result.contains("John"));
        assertTrue(result.contains("userItems"));
        // output: {"id":3,"itemName":"book","owner":{"id":1,"name":"John","userItems":[3]}}
        // userItems 属性使用 id替代
    }
    
    /**
     * - @JsonFilter注释指定一个过滤器序列化过程中使用
     */
    @Test
    void whenSerializingUsingJsonFilter_thenCorrect()
            throws JsonProcessingException {
        BeanWithFilter bean = new BeanWithFilter(1, "My bean");
        
        FilterProvider filters = new SimpleFilterProvider().addFilter(
                "myFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept("name")
        );
        
        String result = new ObjectMapper()
                .writer(filters)
                .writeValueAsString(bean);
        
        assertTrue(result.contains("name"));
        assertFalse(result.contains("id"));
    }
    
    /**
     * 用 @JacksonAnnotationsInside 注释自定义 Jackson 注释
     */
    @Test
    void whenSerializingUsingCustomAnnotation_thenCorrect()
            throws JsonProcessingException {
        BeanWithCustomAnnotation bean = new BeanWithCustomAnnotation(1, "My bean", null);
        
        String result = new ObjectMapper().writeValueAsString(bean);
        
        assertTrue(result.contains("My bean"));
        assertTrue(result.contains("1"));
        assertFalse(result.contains("dateCreated"));
    }
    
    /**
     * 使用 Jackson MixIn 批注，在序列化时忽略 addMixIn 添加的类型
     */
    @Test
    void whenSerializingUsingMixInAnnotation_thenCorrect()
            throws JsonProcessingException {
        Item item = new Item(1, "book", null);
        
        String result = new ObjectMapper().writeValueAsString(item);
        assertTrue(result.contains("owner"));
        
        ObjectMapper mapper = new ObjectMapper();
        // 序列化忽略 User 对象
        mapper.addMixIn(User.class, Item.MyMixInForIgnoreType.class);
        result = mapper.writeValueAsString(item);
        assertFalse(result.contains("owner"));
    }
    
    /**
     * 禁用所有 Jackson 注解
     */
    @Test
    void whenDisablingAllAnnotations_thenAllDisabled()
            throws IOException {
        DisableAnnotationBean bean = new DisableAnnotationBean(1, null);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.USE_ANNOTATIONS);
        String result = mapper.writeValueAsString(bean);
        
        assertTrue(result.contains("1"));
        assertTrue(result.contains("name"));
    }
    
    /**
     * 使用 @JsonAppend 注解，序列化时往 JSON 中添加属性
     */
    @Test
    void whenSerializingUsingJsonAppend_thenCorrect() throws JsonProcessingException {
        BeanWithoutAppend beanWithoutAppend = new BeanWithoutAppend(2, "Bean With Append Annotation");
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerFor(BeanWithoutAppend.class).withAttribute("version", 1.1);
        String result = objectWriter.writeValueAsString(beanWithoutAppend);
        
        assertTrue(result.contains("version"));
    }
    
    /**
     * 使用 @JsonNaming 注解选择序列化属性命名策略
     * 命名策略可以根据 {@link PropertyNamingStrategy}内部类查看
     */
    @Test
    void whenSerializingUsingJsonNaming_thenCorrect() throws JsonProcessingException {
        NamingBean bean = new NamingBean(3, "Naming Bean");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(bean);
        assertTrue(jsonString.contains("bean_name"));
    }
}



