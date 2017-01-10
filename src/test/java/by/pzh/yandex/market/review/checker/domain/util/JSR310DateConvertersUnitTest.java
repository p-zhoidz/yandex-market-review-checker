package by.pzh.yandex.market.review.checker.domain.util;


import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author p.zhoidz.
 */
public class JSR310DateConvertersUnitTest {

    /**
     * Test method.
     * Test case:
     * - pass null as LocalDate
     * Expected result:
     * - null
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void LocalDateToDateConverterCase1() throws Exception {
        JSR310DateConverters.LocalDateToDateConverter instance
                = JSR310DateConverters.LocalDateToDateConverter.INSTANCE;

        Date actualResult = instance.convert(null);
        assertThat(actualResult).isNull();
    }

    /**
     * Test method.
     * Test case:
     * - pass null.
     * Expected result:
     * -  null.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void testDateToLocalDateConverterCase2() throws Exception {
        JSR310DateConverters.DateToLocalDateConverter instance =
                JSR310DateConverters.DateToLocalDateConverter.INSTANCE;

        LocalDate actualResult = instance.convert(null);
        assertThat(actualResult).isNull();
    }

    /**
     * Test method.
     * Test case:
     * - pass 2016.07.13 15:20:13 LocalDate.
     * Expected result:
     * -  2016.07.13 15:20:13 java.util.Date.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void LocalDateTimeToDateConverterCase1() throws Exception {
        JSR310DateConverters.LocalDateTimeToDateConverter instance =
                JSR310DateConverters.LocalDateTimeToDateConverter.INSTANCE;

        LocalDateTime localDateTime = LocalDateTime.of(2016, 7, 13, 15, 20, 13);

        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault().toString()));
        Date date = isoFormat.parse("2016-07-13T15:20:13");

        Date actualResult = instance.convert(localDateTime);
        assertThat(actualResult).isEqualTo(date);
    }

    /**
     * Test method.
     * Test case:
     * - pass null.
     * Expected result:
     * -  null.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void LocalDateTimeToDateConverterCase2() throws Exception {
        JSR310DateConverters.LocalDateTimeToDateConverter instance =
                JSR310DateConverters.LocalDateTimeToDateConverter.INSTANCE;

        Date actualResult = instance.convert(null);
        assertThat(actualResult).isNull();
    }

    /**
     * Test method.
     * Test case:
     * - pass 2016.07.13 15:20:13 java.util.Date.
     * Expected result:
     * -  2016.07.13 15:20:13 LocalDate.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void testDateToLocalDateTimeConverterCase1() throws Exception {
        JSR310DateConverters.DateToLocalDateTimeConverter instance =
                JSR310DateConverters.DateToLocalDateTimeConverter.INSTANCE;

        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault().toString()));
        Date date = isoFormat.parse("2016-07-13T15:20:13");

        LocalDateTime expected = LocalDateTime.of(2016, 7, 13, 15, 20, 13);

        LocalDateTime actual = instance.convert(date);
        assertThat(expected).isEqualTo(actual);

    }

    /**
     * Test method.
     * Test case:
     * - pass null.
     * Expected result:
     * -  null.
     *
     * @throws Exception Standard test method exception cases.
     */
    @Test
    public void testDateToLocalDateTimeConverterCase2() throws Exception {
        JSR310DateConverters.DateToLocalDateTimeConverter instance =
                JSR310DateConverters.DateToLocalDateTimeConverter.INSTANCE;

        LocalDateTime actual = instance.convert(null);
        assertThat(actual).isNull();
    }
}
