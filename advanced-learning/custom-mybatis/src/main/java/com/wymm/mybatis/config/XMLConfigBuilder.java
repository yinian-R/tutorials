package com.wymm.mybatis.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wymm.mybatis.io.Resources;
import com.wymm.mybatis.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 使用 dom4j 将配置文件解析，封装成 Configuration
     *
     * @param inputStream
     * @return
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);

        Element rootElement = document.getRootElement();

        // 解析 dataSource
        Node dataSource = rootElement.selectSingleNode("dataSource");
        List<Node> list = dataSource.selectNodes("//property");
        parseDataSource(configuration, list);

        // 解析 mapper
        List<Node> mapperNodes = rootElement.selectNodes("//mapper");
        parseMapper(configuration, mapperNodes);

        return configuration;
    }

    /**
     * 解析 dataSource
     *
     * @param configuration
     * @param list
     * @throws PropertyVetoException
     */
    public void parseDataSource(Configuration configuration, List<Node> list) throws PropertyVetoException {
        Properties props = new Properties();
        for (Node node : list) {
            Element element = (Element) node;
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            props.setProperty(name, value);
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass((String) props.get("driveClass"));
        comboPooledDataSource.setJdbcUrl((String) props.get("url"));
        comboPooledDataSource.setUser((String) props.get("username"));
        comboPooledDataSource.setPassword((String) props.get("password"));
        configuration.setDataSource(comboPooledDataSource);
    }

    /**
     * 解析 mapper
     *
     * @param configuration
     * @param mapperNodes
     * @throws DocumentException
     */
    public void parseMapper(Configuration configuration, List<Node> mapperNodes) throws DocumentException {
        for (Node mapperNode : mapperNodes) {
            Element mapperElement = (Element) mapperNode;
            String mapperPath = mapperElement.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        }
    }

}
