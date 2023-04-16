package com.phamnam.quickpoll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableWebMvc
public class SwaggerConfiguration {
    @Bean
    public Docket apiV1(){
        return apiVersion("v1");
    }
    @Bean
    public Docket apiV2(){
         return apiVersion("v2");
    }
    @Bean
    public Docket apiV3(){
        return apiVersion("v3");
    }

    public Docket apiVersion(String version){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/"+version+"/*.*"))
                .build()
                .groupName(version)
                .apiInfo(apiInfo(version))
                .useDefaultResponseMessages(false);
    }
    private ApiInfo apiInfo(String version) {
        return new ApiInfo(
                "QuickPoll REST API"  ,
                 "QuickPOll Api for creating and managing polls",
                version,
                "Term of service",
                new Contact("Pham Ba Nam","www.facebook.com/phamnam2210","phambanam1999@gmail.com"),
                "MIT License",
                "http://opensource.org/licenses/MIT",
                Collections.emptyList()
                );
    }
}
