package by.pzh.yandex.market.review.checker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Dummy {@link AuditorAware}. Since application does not support any kind of the 'user'
 * Auditor is null. However is still can be used for
 * {@code @{@link org.springframework.data.annotation.CreatedDate}}
 * and
 * {@code @{@link org.springframework.data.annotation.LastModifiedDate}}.
 *
 * @author p.zhoidz.
 */
@Configuration
@EnableJpaAuditing
public class AuditorConfiguration {

    @Bean
    public AuditorAware auditorProvider() {
        return () -> null;
    }
}
