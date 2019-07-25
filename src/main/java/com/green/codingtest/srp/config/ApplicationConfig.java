package com.green.codingtest.srp.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * class ApplicationConfig
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@SpringBootApplication
@Import({
    DatasourceConfig.class,
    MvcConfig.class,
    SwaggerConfig.class
})
public class ApplicationConfig {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
