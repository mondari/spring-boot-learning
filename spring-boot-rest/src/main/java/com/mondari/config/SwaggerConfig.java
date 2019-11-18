package com.mondari.config;

import com.mondari.RestApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.ArrayList;

import static springfox.documentation.service.ApiInfo.DEFAULT_CONTACT;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())

                // 设置 ApiSelectorBuilder
                .select()
                // 1. 默认 apis 为
                // and(
                //  not(withClassAnnotation(ApiIgnore.class)),
                //  not(withMethodAnnotation(ApiIgnore.class)))
                .apis(RequestHandlerSelectors.basePackage(RestApplication.class.getPackage().getName()))
                // 2. 默认 paths 为
                .paths(PathSelectors.any())
                .build()

                // 添加路径前缀（建议不加或保持为“/”）
                .pathMapping("/")
                // 渲染 model 时将 LocalDate 类型转换为 String 类型
                .directModelSubstitute(LocalDate.class,
                        String.class)
                // 默认关闭（建议），开启后会在URL上显示Query参数
                .enableUrlTemplating(false)
                .tags(new Tag("Pet Service", "All apis relating to pets"))

                ;
    }

    private ApiInfo apiInfo() {
        // 默认
        new ApiInfo("Api Documentation", "Api Documentation", "1.0", "urn:tos",
                DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
        return new ApiInfoBuilder()
                // 一般标题设置为 “xxx API Documentation”
                .title("Spring Boot Rest Documentation")
                .description("description is empty")
                .version("1.0")
                .termsOfServiceUrl("urn:tos")
                .contact(DEFAULT_CONTACT)
                .license("Baidu License(Click to go to Baidu.com)")
                .licenseUrl("http://www.baidu.com")
                .extensions(new ArrayList<>())
                .build();
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                "client-id",
                "client-secret",
                "realm",
                "restApp",
                "apiKey",
                ApiKeyVehicle.HEADER,
                "api_key",
                ",");
    }

}
