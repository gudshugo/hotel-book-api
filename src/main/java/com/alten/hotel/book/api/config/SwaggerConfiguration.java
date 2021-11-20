package com.alten.hotel.book.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class responsible for swagger configuration annotations and methods.
 * @author Hugo Gois
 * @version 1.0
 * @since 1.0
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.alten.hotel.book.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Welcome to Hotel Book API")
                .description(" REST API developed in Java (using Spring Boot) to function as a booking system for a hotel. This project is a technical test part of the Alten Group recruitment process.")
                .version("1.0")
                .contact(new Contact("Hugo Gois","https://www.linkedin.com/in/hugo-gois/", "gudshugo@gmail.com"))
                .build();
    }

}
