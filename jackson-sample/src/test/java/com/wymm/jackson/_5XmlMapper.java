package com.wymm.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wymm.jackson.xml.Address;
import com.wymm.jackson.xml.Person;
import com.wymm.jackson.xml.SimpleBean;
import com.wymm.jackson.xml.SimpleBeanForCapitalizedFields;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 研究如何将简单的 POJO 序列化成 XML，并且从基本的 XML 反序列化成 POJO
 */
class _5XmlMapper {
    
    /**
     * 序列化为XML 字符串
     */
    @Test
    void whenJavaSerializedToXmlStr_thenCorrect() throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(new SimpleBean());
        assertNotNull(xml);
        // output: <SimpleBean><x>1</x><y>2</y></SimpleBean>
    }
    
    /**
     * 序列化到XML文件
     */
    @Test
    void whenJavaSerializedToXmlFile_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File("target/simple_bean.xml"), new SimpleBean());
        File file = new File("target/simple_bean.xml");
        assertNotNull(file);
    }
    
    /**
     * XML String反序列化回Java对象
     */
    @Test
    void whenJavaGotFromXmlStr_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        SimpleBean simpleBean = xmlMapper.readValue("<SimpleBean><x>1</x><y>2</y></SimpleBean>", SimpleBean.class);
        assertTrue(simpleBean.getX() == 1 && simpleBean.getY() == 2);
    }
    
    /**
     * XML文件，则可以将其转换回Java对象
     */
    @Test
    void whenJavaGotFromXmlFile_thenCorrect() throws IOException {
        URL file = this.getClass().getResource("/simple_bean.xml");
        XmlMapper xmlMapper = new XmlMapper();
        SimpleBean simpleBean = xmlMapper.readValue(file, SimpleBean.class);
        assertTrue(simpleBean.getX() == 1 && simpleBean.getY() == 2);
    }
    
    /**
     * 为了正确处理大写元素，我们需要在@xsonProperty注释中注释“ x”字段
     */
    @Test
    void whenJavaGotFromXmlStrWithCapitalElem_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        SimpleBeanForCapitalizedFields value = xmlMapper.readValue(
                "<SimpleBeanForCapitalizedFields><X>1</X><y>2</y></SimpleBeanForCapitalizedFields>",
                SimpleBeanForCapitalizedFields.class);
        assertTrue(value.getX() == 1 && value.getY() == 2);
    }
    
    /**
     * 电话号码封装在phoneNumbers包装器中，而address则没有，可以通过Person类中的@JacksonXMLElementWrapper注释控制
     * 还可以使用@JacksonXmlElementWrapper(localName ='phoneNumbers')更改包装元素名称
     */
    @Test
    void whenJavaSerializedToXmlFile_thenSuccess() throws IOException {
        String XML = "<Person><firstName>Rohan</firstName><lastName>Daye</lastName><phoneNumbers><phoneNumbers>9911034731</phoneNumbers><phoneNumbers>9911033478</phoneNumbers></phoneNumbers><address><streetName>Name1</streetName><city>City1</city></address><address><streetName>Name2</streetName><city>City2</city></address></Person>";
    
        Person person = new Person();
        person.setFirstName("Rohan");
        person.setLastName("Daye");
        person.addPhoneNumber("9911034731");
        person.addPhoneNumber("9911033478");
        Address address = new Address();
        address.setCity("City1");
        address.setStreetName("Name1");
        person.addAddress(address);
        address = new Address();
        address.setCity("City2");
        address.setStreetName("Name2");
        person.addAddress(address);
    
        XmlMapper xmlMapper = new XmlMapper();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMapper.writeValue(byteArrayOutputStream, person);
        assertEquals(XML, byteArrayOutputStream.toString());
    
    
        // jackson 也可以读取包含对象列表的 XML
        Person value = xmlMapper.readValue(XML, Person.class);
        assertEquals("City1", value.getAddress().get(0).getCity());
        assertEquals("City2", value.getAddress().get(1).getCity());
    }
}
