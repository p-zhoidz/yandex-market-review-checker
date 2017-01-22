package by.pzh.yandex.market.review.checker;

import by.pzh.yandex.market.review.checker.config.ApplicationProperties;
import by.pzh.yandex.market.review.checker.config.LoggingAspectConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author p.zhoidz.
 */
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(value = LoggingAspectConfiguration.class, type = FilterType.ASSIGNABLE_TYPE)})
@EnableAutoConfiguration
@EnableConfigurationProperties({ApplicationProperties.class, LiquibaseProperties.class})
@ContextConfiguration("/config/application.yml")
public class ApplicationTestContext extends WebMvcConfigurerAdapter {
}