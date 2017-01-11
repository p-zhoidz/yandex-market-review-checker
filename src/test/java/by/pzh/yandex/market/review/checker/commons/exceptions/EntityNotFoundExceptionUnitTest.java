package by.pzh.yandex.market.review.checker.commons.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author p.zhoidz.
 */
public class EntityNotFoundExceptionUnitTest {

    /**
     * Dummy class for test purpose.
     */
    private static class TestNotFoundable {

    }

    /**
     * Test method.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void testExpectedMessage() throws Exception {
        EntityNotFoundException sut = new EntityNotFoundException(TestNotFoundable.class, "with id = " + 1);

        assertEquals(TestNotFoundable.class.getName(), sut.getClassName());
        assertEquals("Entity by.pzh.yandex.market.review.checker.commons.exceptions." +
                        "EntityNotFoundExceptionUnitTest$TestNotFoundable with id = 1 value does not exist.",
                sut.getMessage());
    }
}