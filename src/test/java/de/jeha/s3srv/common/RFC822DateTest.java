package de.jeha.s3srv.common;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RFC822DateTest {

    @Test
    public void testParse() {
        final Date date = RFC822Date.parse("Wed, 29 Jun 2016 20:18:33 GMT");
        assertEquals(1467231513000L, date.getTime());
    }

    @Test
    public void testFormat() {
        final String date = RFC822Date.format(new Date(1467231513000L));
        assertEquals("Wed, 29 Jun 2016 20:18:33 GMT", date);
    }

}
