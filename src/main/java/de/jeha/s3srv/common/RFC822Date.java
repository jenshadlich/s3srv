package de.jeha.s3srv.common;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.tz.FixedDateTimeZone;

import java.util.Date;
import java.util.Locale;

/**
 * @author jenshadlich@googlemail.com
 */
public class RFC822Date {
    private static final DateTimeZone GMT = new FixedDateTimeZone("GMT", "GMT", 0, 0);

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'")
                    .withLocale(Locale.US)
                    .withZone(GMT);

    public static Date parse(String date) {
        return new Date(DATE_FORMAT.parseMillis(date));
    }

    public static String format(Date date) {
        return DATE_FORMAT.print(date.getTime());
    }

}
