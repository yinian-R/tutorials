package com.wymm.springboottemplate.core.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * MyBatis 代码生成器
 */
public class MyBatisGenerator {
    
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        // 生成文件所在包名
        String packagePath = "com.wymm.springboottemplate.web";
        // 作者名称
        String author = "wymm";
        // 指定生成表，不写默认生成所有
        String[] includeTables = new String[]{"book", "book_type"};
        
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        
        // set freemarker engine
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        
        generator
                .setDataSource(
                        // 数据源配置
                        new DataSourceConfig()
                                .setDbType(DbType.MYSQL)// 数据库类型
                                .setTypeConvert(new MySqlTypeConvert())
                                .setDriverName("com.mysql.cj.jdbc.Driver")
                                .setUsername("root")
                                .setPassword("root")
                                .setUrl("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8")
                )
                .setGlobalConfig(
                        // 全局配置
                        new GlobalConfig()
                                .setOutputDir(projectPath + "/src/main/java")//输出目录
                                .setFileOverride(false)// 是否覆盖文件
                                .setActiveRecord(true)// 开启 activeRecord 模式
                                .setEnableCache(false)// XML 二级缓存
                                .setBaseResultMap(true)// XML ResultMap
                                .setBaseColumnList(false)// XML columList
                                .setAuthor(author)
                                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                                
                                //.setMapperName("%sMapper")
                                //.setXmlName("%sMapper")
                                .setServiceName("%sService")
                                .setServiceImplName("%sServiceImpl")
                        //.setControllerName("%sController")
                )
                .setPackageInfo(
                        // 包配置
                        new PackageConfig()
                                .setParent(packagePath)// 自定义包路径
                                .setEntity("model.entity")
                                .setController("controller")// 这里是控制器包名
                                .setService("service")
                                .setServiceImpl("service.impl")
                                .setMapper("mapper")
                                .setXml("mapper")
                )
                .setStrategy(
                        // 策略配置
                        new StrategyConfig()
                                // .setCapitalMode(true)// 全局大写命名
                                // .setDbColumnUnderline(true)//全局下划线命名
                                //.setTablePrefix("wp_")// 此处可以修改为您的表前缀
                                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                                .setInclude(includeTables) //指定生成表，注释掉或不写，默认生成所有
                                // .setExclude(new String[]{"test"}) // 排除生成的表
                                // 自定义实体父类
                                // .setSuperEntityClass("com.baomidou.demo.TestEntity")
                                // 自定义实体，公共字段
                                // .setSuperEntityColumns(new String[]{"id"})
                                // 自定义需要填充的字段
                                //.setTableFillList(Stream.of(
                                //        new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE)
                                //).collect(Collectors.toList()))
                                
                                // 使用 lombok
                                .setEntityLombokModel(true)
                                // 生成默认的 serialVersionUID
                                .setEntitySerialVersionUID(false)
                                
                                .setRestControllerStyle(true)
                )
        //.setCfg(
        //        //注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        //        new InjectionConfig() {
        //            @Override
        //            public void initMap() {
        //                Map<String, Object> map = new HashMap<>();
        //                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "");
        //                this.setMap(map);
        //            }
        //        }
        //                .setFileOutConfigList(Collections.singletonList(new FileOutConfig("/templates/mapper.xml.ftl") {
        //                    // 自定义输出文件目录
        //                    @Override
        //                    public String outputFile(TableInfo tableInfo) {
        //                        return projectPath + "/src/main/java" + "/" + packagePath + "/" + tableInfo.getEntityName() + "Dao.xml";
        //                    }
        //                }))
        //)
        //.setTemplate(
        //        // 关闭默认 xml 生成，调整生成 至 根目录
        //        new TemplateConfig().setXml(null)
        //)
        ;
        
        // 执行生成
        generator.execute();
        
        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        //System.err.println(generator.getCfg().getMap().get("abc"));
        
    }
}
