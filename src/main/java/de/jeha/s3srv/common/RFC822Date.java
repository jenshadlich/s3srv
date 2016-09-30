package de.jeha.s3srv.common;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.tz.FixedDateTimeZone;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;

/**
 * @author jenshadlich@googlemail.com
 */
public class RFC822Date {

    private static final DateTimeZone GMT = new FixedDateTimeZone("GMT", "GMT", 0, 0);

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'")
            .withLocale(Locale.US)
            .withZone(GMT);

    /**
     * @param dateString parse the given string
     * @return Date representation
     */
    public static Date parse(String dateString) {
        return new Date(DATE_FORMAT.parseMillis(dateString));
    }

    /**
     * @param date the date
     * @return string representation of the given date
     */
    public static String format(Date date) {
        return DATE_FORMAT.print(date.getTime());
    }

    /**
     * @param instant the instant
     * @return string representation of the given instant
     */
    public static String format(Instant instant) {
        return DATE_FORMAT.print(Date.from(instant).getTime());
    }

}
