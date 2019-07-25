package com.green.codingtest.srp;

import com.green.codingtest.srp.config.DatasourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * class TestConfig
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
@Configuration
@PropertySource("classpath:/test.properties")
@Import({
    DatasourceConfig.class
})
public class DatasourceTestConfig {
    @Bean
    PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
