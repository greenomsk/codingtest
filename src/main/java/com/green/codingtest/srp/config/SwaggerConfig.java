package com.green.codingtest.srp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.emptyList;

/**
 * class SwaggerConfig
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("scissors-rock-paper")
            .apiInfo(
                new ApiInfo(
                    "Scissors-Rock-Paper Game",
                    "Scissors-Rock-Paper Game Coding Test",
                    "v1.0",
                    "",
                    new Contact("", "", "") ,
                    "",
                    "",
                    emptyList()
                )
            )
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/1.0/scissors-rock-paper/.*"))
            .build()
            .genericModelSubstitutes(ResponseEntity.class)
            .useDefaultResponseMessages(false);
    }
}
