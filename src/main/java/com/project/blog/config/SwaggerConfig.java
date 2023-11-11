package com.project.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER="Authorization";

    private ApiKey apiKeys(){
        return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
    }
    private List<SecurityContext> securityContextList(){
        return Arrays.asList( SecurityContext
                .builder()
                .securityReferences(securityReferences()).build());
    }
    private List<SecurityReference> securityReferences(){
        AuthorizationScope scopes=new AuthorizationScope("global","accessEveryThing");
        return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[]{scopes}));
    }

    @Bean
    public Docket api(){


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContextList())
                .securitySchemes()
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getInfo() {
        return new ApiInfo("Blogging Bakend Application",
                "This project is for learning purpose",
                "1.0",
                "Terms of Service",
                new Contact("Gagan",null,"gagandeepvashisht96@gmail.com"),
                "License",
                null,
                Collections.emptyList());
    }

}
