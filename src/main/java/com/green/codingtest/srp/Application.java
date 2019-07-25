package com.green.codingtest.srp;

import com.green.codingtest.srp.config.ApplicationConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * class Application
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
public class Application {
    /**
     * Main method
     * @param args cmd line arguments
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .banner((environment, sourceClass, out) -> out.println("<<<  Scissors-Rock-Paper  >>>"))
                .sources(ApplicationConfig.class)
                .run(args);
    }
}
