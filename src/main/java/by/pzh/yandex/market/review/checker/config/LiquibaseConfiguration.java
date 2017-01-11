package by.pzh.yandex.market.review.checker.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Liquibase Configuration.
 *
 * @author p.zhoidz.
 */
@Configuration
public class LiquibaseConfiguration {

    /**
     * Creates and configure {@link SpringLiquibase}.
     *
     * @param dataSource          data source
     * @param liquibaseProperties liquibase properties
     * @return configured {@link SpringLiquibase} instance
     */
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, LiquibaseProperties liquibaseProperties) {

        // Use liquibase.integration.spring.SpringLiquibase if you don't want Liquibase to start asynchronously
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());

        liquibase.setShouldRun(liquibaseProperties.isEnabled());


        return liquibase;
    }
}
