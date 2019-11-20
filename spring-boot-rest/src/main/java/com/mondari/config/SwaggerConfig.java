package com.mondari.config;

import com.mondari.RestApplication;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static springfox.documentation.service.ApiInfo.DEFAULT_CONTACT;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        // HTTP 响应信息（显示在Swagger界面上的“Response Messages”信息），
        // 默认请参考 springfox.documentation.spi.service.contexts.Defaults.java#initResponseMessages()
        final List<ResponseMessage> globalResponses = Arrays.asList(
                new ResponseMessageBuilder()
                        .code(OK.value())
                        .message(OK.getReasonPhrase())
                        .build(),
                new ResponseMessageBuilder()
                        .code(INTERNAL_SERVER_ERROR.value())
                        .message(INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .build());

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

                // 默认关闭（建议），开启后会在URL上显示QueryParam参数
                // An example of this would be:
                // http://example.org/findCustomersBy?name=Test to find customers by name.
                // This would be represented as http://example.org/findCustomersBy{?name}.
                .enableUrlTemplating(false)

                // 渲染 model 时将 LocalDate 类型转换为 String 类型
                .directModelSubstitute(LocalDate.class, String.class)
                // 参考 http://springfox.github.io/springfox/docs/current/#answers-to-common-questions-and-problems
//                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
//                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)

                // 设置自定义响应信息
//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, globalResponses)
//                .globalResponseMessage(RequestMethod.POST, globalResponses)
//                .globalResponseMessage(RequestMethod.DELETE, globalResponses)
//                .globalResponseMessage(RequestMethod.PATCH, globalResponses)
//                .globalResponseMessage(RequestMethod.PUT, globalResponses)

                // 设置安全相关
                // Sets up the security schemes used to protect the apis.
                // Supported schemes are ApiKey, BasicAuth and OAuth
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(newArrayList(securityContext()))
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

    /**
     * 点击 Swagger UI 界面中的 Authorize会显示
     * name: HttpHeaders.AUTHORIZATION 的值
     * in: In.HEADER.name() 的值
     * value: 用户输入
     * @return
     */
    private ApiKey apiKey() {
        return new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "description");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("reference", authorizationScopes));
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                "clientId",
                "clientSecret",
                "realm",
                "appName",
                // HTTP请求的时候会将 apiKey:apiKeyName 放到 Header 中
                "apiKey",
                ApiKeyVehicle.HEADER,
                "apiKeyName",
                ",");
    }

}
