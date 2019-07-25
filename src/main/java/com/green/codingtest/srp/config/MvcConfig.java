package com.green.codingtest.srp.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.green.codingtest.srp.controller.rest.GameController;
import com.green.codingtest.srp.controller.rest.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;

/**
 * class MvcConfig
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {
    GameController.class,
    GlobalExceptionHandler.class
})
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper());
        converters.add(jsonConverter);
    }

    @Bean
    ObjectMapper objectMapper() {
        return objectMapperBuilder().build();
    }

    @Bean
    Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
            .dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"))
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .featuresToEnable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .failOnUnknownProperties(false);
    }

    @Bean
    MappingJackson2HttpMessageConverter jackson2Converter() {
        final MappingJackson2HttpMessageConverter converter =
            new MappingJackson2HttpMessageConverter(objectMapper());
        converter.setSupportedMediaTypes(
            asList(new MediaType("application", "json", UTF_8),
                new MediaType("application", "*+json", UTF_8)));
        converter.setPrettyPrint(true);
        return converter;
    }
}
