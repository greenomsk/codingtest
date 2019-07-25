package com.green.codingtest.srp.config;

import com.green.codingtest.srp.service.GameService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * class ServiceConfig
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
@Configuration
@ComponentScan(basePackageClasses = GameService.class)
public class ServiceConfig {
}
