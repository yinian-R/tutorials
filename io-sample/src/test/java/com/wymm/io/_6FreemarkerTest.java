package com.wymm.io;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * freemarker 模板引擎
 */
public class _6FreemarkerTest {
    
    /**
     * freemarker 使用简单示例
     */
    @Test
    public void simple() throws IOException, TemplateException {
        String sourceFile = TestFileUtil.getTestResourcePath() + "ftl/hello.ftl";
        String content = FileUtil.readString(sourceFile, StandardCharsets.UTF_8);
        
        
        // 创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("hello", "this is my first FreeMarker test.");
        dataModel.put("users", Stream.of("A", "B", "C").collect(Collectors.toList()));
        dataModel.put("isAdmin", true);
        
        
        Writer out = new StringWriter();
        StringReader reader = new StringReader(content);
        {
            // 加载一个模板，创建一个模板对象。
            Template template = new Template("hello.html", reader, null, StandardCharsets.UTF_8.name());
            // 调用模板对象的process方法输出
            template.process(dataModel, out);
        }
        
        String outContent = out.toString();
        System.out.println(outContent);
        
        // 关闭流。
        out.close();
    }
    
}
