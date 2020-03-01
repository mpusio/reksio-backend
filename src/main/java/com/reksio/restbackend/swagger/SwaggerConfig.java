package com.reksio.restbackend.swagger;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket SwaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("com.reksio")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(getSwaggerPaths())
                .build()
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Pets rest api")
                .description("Api to buy/sell/adopt pets. \n" +
                        "Api contain also things like charging, email sending, embedded simple blog and much, much more. \n" +
                        "For deeper understanding check readme file in repository (if you have access): https://github.com/mpusio/reksio-backend  \n " +
                        "\n" +
                        "In case of problems write to my email.\n " +
                        "Example account to login as user: \n" +
                        "login: someuser@gmail.com\n" +
                        "password: P@ssword123\n" +
                        "Have a fun ;)")
                .contact(new Contact("Michal Pusio",
                        "https://github.com/mpusio",
                        "pusiomichal@gmail.com"))
                .version("1.0")
                .build();
    }

    private Predicate<String> getSwaggerPaths() {
        return or(
                regex("/api.*"),
                regex("/test.*"));
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(tokenAuth())
                .forPaths(or(
                        regex("/api/v1/charge"),
                        regex("/api/v1/user"),
                        regex("/api/v1/user/advertisement.*"),
                        regex("/api/v1/user/password"),
                        regex("/api/v1/user/blog.*")
                ))
                .build();
    }

    List<SecurityReference> tokenAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("user", "Access to features included in token");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }
}
