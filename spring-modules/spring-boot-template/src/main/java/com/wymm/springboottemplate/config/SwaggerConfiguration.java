package com.wymm.springboottemplate.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    
    @Resource
    private OpenApiExtensionResolver openApiExtensionResolver;
    
    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 使其在文档中显示为 String 格式
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(ChronoUnit.class, String.class)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .extensions(openApiExtensionResolver.buildSettingExtensions());
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RESTful APIs")
                .description("# RESTful APIs")
                .version("1.0")
                .build();
    }
}