package com.wymm.mybatis.config;

import com.wymm.mybatis.pojo.Configuration;
import com.wymm.mybatis.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    
    private Configuration configuration;
    
    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
    
    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        
        List<Node> selectNodes = rootElement.selectNodes("//select");
        for (Node selectNode : selectNodes) {
            Element selectElement = (Element) selectNode;
            String id = selectElement.attributeValue("id");
            String resultType = selectElement.attributeValue("resultType");
            String paramterType = selectElement.attributeValue("paramterType");
            String sql = selectElement.getTextTrim();
            
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setSql(sql);
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }
    }
    
}
