//package com.wymm.cache.jetcache.config;
//
//import cn.hutool.core.util.StrUtil;
//import io.swagger.annotations.Api;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.autoconfigure.web.ServerProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.temporal.ChronoUnit;
//
//@Slf4j
//@EnableOpenApi
//@RequiredArgsConstructor
//@Configuration
//public class SwaggerConfiguration implements CommandLineRunner {
//
//    private final ServerProperties serverProperties;
//
//    @Override
//    public void run(String... args) {
//        try {
//            System.out.println("\n\n接口文档访问地址:");
//            // 打印接口文档访问链接
//            System.out.println("\thttp://127.0.0.1"
//                    + ":"
//                    + serverProperties.getPort()
//                    + StrUtil.toStringOrEmpty(serverProperties.getServlet().getContextPath())
//                    + "/doc.html\n\n");
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                .directModelSubstitute(LocalDateTime.class, String.class)
//                .directModelSubstitute(LocalDate.class, String.class)
//                .directModelSubstitute(LocalTime.class, String.class)
//                .directModelSubstitute(ChronoUnit.class, String.class)
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("RESTful APIs")
//                .description("# RESTful APIs")
//                .version("1.0")
//                .build();
//    }
//}