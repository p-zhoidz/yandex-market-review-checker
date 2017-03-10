package by.pzh.yandex.market.review.checker.security;

import by.pzh.yandex.market.review.checker.config.settings.ApplicationProperties;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author p.zhoidz.
 */
public class SimpleCorsFilterUnitTest {
    /**
     * Test method.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void testDoFilter() throws Exception {
        String baseUrl = "baseUrl";

        ApplicationProperties mockProperties = mock(ApplicationProperties.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        FilterChain mockChain = mock(FilterChain.class);
        ApplicationProperties.Constants mockConstants = mock(ApplicationProperties.Constants.class);

        when(mockProperties.getConstants()).thenReturn(mockConstants);
        when(mockConstants.getBaseUrl()).thenReturn(baseUrl);

        SimpleCorsFilter sut = new SimpleCorsFilter(mockProperties);

        sut.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockResponse).setHeader("Access-Control-Allow-Origin", baseUrl);
        verify(mockResponse).setHeader("Access-Control-Allow-Credentials", "true");
        verify(mockResponse).setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        verify(mockResponse).setHeader("Access-Control-Max-Age", "3600");
        verify(mockResponse).setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        verify(mockResponse).setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        verify(mockChain).doFilter(mockRequest, mockResponse);

        verifyNoMoreInteractions(mockChain, mockRequest, mockResponse);
    }

}