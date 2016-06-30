package de.jeha.s3srv.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * @author jenshadlich@googlemail.com
 */
public class InstantXmlAdapter extends XmlAdapter<String, Instant> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    @Override
    public Instant unmarshal(String value) throws Exception {
        return value != null ? formatter.parse(value, Instant::from) : null;
    }

    @Override
    public String marshal(Instant value) throws Exception {
        return value != null ? formatter.format(value) : null;
    }

}