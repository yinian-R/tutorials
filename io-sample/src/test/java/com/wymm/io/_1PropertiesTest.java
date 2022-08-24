package com.wymm.io;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 使用 {@link PropertiesLoaderUtils#loadProperties(Resource)} 可以省略判断文件是properties和xml 加载文件属性
 */
class _1PropertiesTest {
    
    /**
     * 打印系统属性
     */
    @Test
    void printSystemProperties() {
        Properties properties = System.getProperties();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            Object o = enumeration.nextElement();
            String property = properties.getProperty((String) o);
            System.out.println("-------------------------------------" + o + "\n" + property);
        }
    }
    
    /**
     * 读取 resources 目录下的 properties 文件
     */
    @Test
    void readResourcesDirFile() throws IOException {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/DispatcherServlet.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        properties.list(System.out);
    }
    
    /**
     * 读取绝对路径的 properties 文件
     */
    @Test
    void readPropertiesFile() throws IOException {
        String dir = System.getProperty("user.dir");
        FileInputStream fileInputStream = new FileInputStream(dir + "\\src\\test\\resources\\DispatcherServlet.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        properties.list(System.out);
        
        System.out.println("\n" + properties.getProperty("org.springframework.web.servlet.HandlerAdapter"));
    }
    
    /**
     * 读取 xml 文件
     */
    @Test
    void readXmlFile() throws IOException {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/simple.xml");
        Properties properties = new Properties();
        properties.loadFromXML(resourceAsStream);
        properties.list(System.out);
    }
    
}