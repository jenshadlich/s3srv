package de.jeha.s3srv.api;

import de.jeha.s3srv.xml.JaxbMarshaller;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * @author jenshadlich@googlemail.com
 */
public class ListAllMyBucketsResponseSerializationTest {

    @Test
    public void test() throws JAXBException, IOException {
        ListAllMyBucketsResponse response = new ListAllMyBucketsResponse(
                new ListAllMyBucketsResponse.Owner("foo", "bar"),
                Collections.singletonList(new ListAllMyBucketsResponse.BucketsEntry(
                        "test",
                        Instant.parse("2006-02-03T16:45:09.001Z"))));

        final String responseXml = JaxbMarshaller.marshall(response);

        System.out.println(responseXml);

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListAllMyBucketsResponse xmlns=\"http://s3.amazonaws.com/doc/2006-03-01\">\n" +
                "    <Owner>\n" +
                "        <ID>foo</ID>\n" +
                "        <DisplayName>bar</DisplayName>\n" +
                "    </Owner>\n" +
                "    <Buckets>\n" +
                "        <Bucket>\n" +
                "            <Name>test</Name>\n" +
                "            <CreationDate>2006-02-03T16:45:09.001Z</CreationDate>\n" +
                "        </Bucket>\n" +
                "    </Buckets>\n" +
                "</ListAllMyBucketsResponse>";

        assertEquals(expected, responseXml);
    }
}
