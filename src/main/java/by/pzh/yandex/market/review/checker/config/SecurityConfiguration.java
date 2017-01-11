package by.pzh.yandex.market.review.checker.config;

import by.pzh.yandex.market.review.checker.security.SimpleCorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import javax.inject.Inject;

/**
 * Application security configuration.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private SimpleCorsFilter simpleCorsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .addFilterBefore(simpleCorsFilter, ChannelProcessingFilter.class)
                .authorizeRequests()
                .antMatchers("/api/**").permitAll();
    }

}