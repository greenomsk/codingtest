package com.green.codingtest.srp.config;

import com.green.codingtest.srp.datasource.dao.MoveLogDao;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.conf.RenderKeywordStyle;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.jooq.SQLDialect.H2;

/**
 * class DatasourceConfig
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@Configuration
@ComponentScan(basePackageClasses = {
    MoveLogDao.class
})
@EnableTransactionManagement
public class DatasourceConfig {
    @Value("${jdbc.driver.class.name}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.user}")
    private String user;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean
    @Primary
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(dataSource());
    }

    /**
     * Returns FlyWay class instance.
     * @return FlyWay class instance.
     */
    @Bean(initMethod = "migrate")
    Flyway flyway() {
        final Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource());
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("classpath:db/migration");
        return flyway;
    }

    /**
     * DSL context
     * @return DSL context
     */
    @Bean
    @DependsOn("flyway")
    DSLContext dslContext() {
        final DefaultConfiguration jooqConfig = new DefaultConfiguration();
        jooqConfig.set(new DataSourceConnectionProvider(transactionAwareDataSource()));
        jooqConfig.setSQLDialect(H2);
        final Settings settings = new Settings();
        settings.setRenderFormatted(true);
        settings.setRenderNameStyle(RenderNameStyle.LOWER);
        settings.setRenderKeywordStyle(RenderKeywordStyle.UPPER);
        jooqConfig.set(settings);
        return new DefaultDSLContext(jooqConfig);
    }

}
