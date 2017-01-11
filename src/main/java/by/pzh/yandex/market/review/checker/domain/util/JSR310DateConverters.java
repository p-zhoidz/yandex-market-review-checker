package by.pzh.yandex.market.review.checker.domain.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * JSR310DateConverters class.
 */
public final class JSR310DateConverters {

    /**
     * Default final class constructor.
     */
    private JSR310DateConverters() {
    }

    /**
     * LocalDateToDateConverter class.
     */
    public static final class LocalDateToDateConverter implements Converter<LocalDate, Date> {

        public static final LocalDateToDateConverter INSTANCE = new LocalDateToDateConverter();

        /**
         * Default final class constructor.
         */
        private LocalDateToDateConverter() {
        }

        @Override
        public Date convert(LocalDate source) {
            if (source == null) {
                return null;
            }
            return Date.from(source.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }

    /**
     * DateToLocalDateConverter class.
     */
    public static final class DateToLocalDateConverter implements Converter<Date, LocalDate> {
        public static final DateToLocalDateConverter INSTANCE = new DateToLocalDateConverter();

        /**
         * Default final class constructor.
         */
        private DateToLocalDateConverter() {
        }

        @Override
        public LocalDate convert(Date source) {
            if (source == null) {
                return null;
            }
            return ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault()).toLocalDate();
        }
    }

    /**
     * ZonedDateTimeToDateConverter class.
     */
    public static final class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {
        public static final ZonedDateTimeToDateConverter INSTANCE = new ZonedDateTimeToDateConverter();

        /**
         * Default final class constructor.
         */
        private ZonedDateTimeToDateConverter() {
        }

        @Override
        public Date convert(ZonedDateTime source) {
            if (source == null) {
                return null;
            }
            return Date.from(source.toInstant());
        }
    }

    /**
     * DateToZonedDateTimeConverter class.
     */
    public static final class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {
        public static final DateToZonedDateTimeConverter INSTANCE = new DateToZonedDateTimeConverter();

        /**
         * Default final class constructor.
         */
        private DateToZonedDateTimeConverter() {
        }

        @Override
        public ZonedDateTime convert(Date source) {
            if (source == null) {
                return null;
            }
            return ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
        }
    }

    /**
     * LocalDateTimeToDateConverter class.
     */
    public static final class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {
        public static final LocalDateTimeToDateConverter INSTANCE = new LocalDateTimeToDateConverter();

        /**
         * Default final class constructor.
         */
        private LocalDateTimeToDateConverter() {
        }

        @Override
        public Date convert(LocalDateTime source) {
            if (source == null) {
                return null;
            }
            return Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
        }
    }

    /**
     * DateToLocalDateTimeConverter class.
     */
    public static final class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {
        public static final DateToLocalDateTimeConverter INSTANCE = new DateToLocalDateTimeConverter();

        /**
         * Default final class constructor.
         */
        private DateToLocalDateTimeConverter() {
        }

        @Override
        public LocalDateTime convert(Date source) {
            if (source == null) {
                return null;
            }
            return LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
        }
    }
}
