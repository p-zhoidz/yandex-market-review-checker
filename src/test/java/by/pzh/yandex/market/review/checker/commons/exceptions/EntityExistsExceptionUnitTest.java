package by.pzh.yandex.market.review.checker.commons.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author p.zhoidz.
 */
public class EntityExistsExceptionUnitTest {

    /**
     * Test method.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void testMessage() throws Exception {
        String entityName = "entityName";
        String entityValue = "entityValue";

        EntityExistsException sut = new EntityExistsException(entityName, entityValue);

        assertEquals("entityName entityValue already exists.", sut.getMessage());
    }
}