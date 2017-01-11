package by.pzh.yandex.market.review.checker.aop.interceptors.handlers;

import by.pzh.yandex.market.review.checker.aop.interceptors.handlers.UniqueDBConstraintViolationInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;

/**
 * @author p.zhoidz.
 */
public class UniqueDBConstraintViolationAspectUnitTest {


    /**
     * Test method.
     * Test case:
     * - Get value from the vocabulary for existing key.
     * Expected result:
     * - Value for the specified key is returned.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void testCase1() throws Exception {
        String expectedResult = "E-mail";
        String actual = UniqueDBConstraintViolationInterceptor.ConstraintVocabulary.getValue("user_email_UNIQUE");
        assertEquals(expectedResult, actual);
    }

    /**
     * Test method.
     * Test case:
     * - Get value from the vocabulary for non existing key.
     * Expected result:
     * - NoSuchElementException is thrown.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test(expected = NoSuchElementException.class)
    public void testCase2() throws Exception {
        UniqueDBConstraintViolationInterceptor.ConstraintVocabulary.getValue("nonExistingKey");
    }

    /**
     * Test method.
     * Test case:
     * - Normal flow. No exceptions thrown.
     * Expected result:
     * - Normal execution. PointCut is not executed.
     *
     * @throws Throwable Standard test method exception cases.
     */
    @Test
    public void testCase3() throws Throwable {
        UniqueDBConstraintViolationInterceptor sut = spy(new UniqueDBConstraintViolationInterceptor());
        ProceedingJoinPoint proceedingJoinPointMock = mock(ProceedingJoinPoint.class);

        sut.handleViolation(proceedingJoinPointMock);
        verify(sut, never()).getDuplicateEntry("");
    }

    /**
     * Test method.
     * Test case:
     * - Normal flow. No exception not related to the constraint violation is thrown.
     * Expected result:
     * - Exception is rethrown.
     *
     * @throws Throwable Standard test method exception cases.
     */
    @Test(expected = RuntimeException.class)
    public void testCase4() throws Throwable {
        UniqueDBConstraintViolationInterceptor sut = spy(new UniqueDBConstraintViolationInterceptor());
        ProceedingJoinPoint proceedingJoinPointMock = mock(ProceedingJoinPoint.class);

        doThrow(new RuntimeException()).when(proceedingJoinPointMock);
        proceedingJoinPointMock.proceed(any(Object[].class));

        sut.handleViolation(proceedingJoinPointMock);
    }

    /**
     * Test method.
     * Test case:
     * - Constraint violation case.
     * Expected result:
     * - Constraint detected and appropriate response is sent to the client.
     *
     * @throws Throwable Standard test method exception cases.
     */
    @Test
    public void testCase5() throws Throwable {
        String expectedHeader = "A record with E-mail \"Test\" already exists. Please change it and try again.";
        String sqlExceptionMessage = "Duplicate entry for 'Test' for key 'name'";

        UniqueDBConstraintViolationInterceptor sut = spy(new UniqueDBConstraintViolationInterceptor());
        ProceedingJoinPoint proceedingJoinPointMock = mock(ProceedingJoinPoint.class);
        RuntimeException mockException = mock(RuntimeException.class);

        doThrow(mockException).when(proceedingJoinPointMock);
        proceedingJoinPointMock.proceed(any(Object[].class));

        doReturn(new ConstraintViolationException("message", new SQLException(sqlExceptionMessage), "user_email_UNIQUE"))
                .when(mockException);
        mockException.getCause();

        ResponseEntity<?> responseEntity = sut.handleViolation(proceedingJoinPointMock);
        System.out.print(responseEntity);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals(Collections.unmodifiableList(Collections.singletonList(expectedHeader)),
                responseEntity.getHeaders().get("X-YMRCApp-alert"));
    }

}
