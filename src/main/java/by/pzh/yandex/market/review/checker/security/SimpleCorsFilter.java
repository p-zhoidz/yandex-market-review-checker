package by.pzh.yandex.market.review.checker.security;

import by.pzh.yandex.market.review.checker.config.settings.ApplicationProperties;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by d.tarasenka.
 */
@Component
public class SimpleCorsFilter implements Filter {

    private ApplicationProperties applicationProperties;

    /**
     * Constructor for DI.
     *
     * @param applicationProperties Application properties instance.
     */
    @Inject
    public SimpleCorsFilter(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", applicationProperties.getConstants().getBaseUrl());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}