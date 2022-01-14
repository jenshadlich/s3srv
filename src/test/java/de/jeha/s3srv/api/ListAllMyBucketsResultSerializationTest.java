package de.jeha.s3srv.api;

import de.jeha.s3srv.xml.JaxbMarshaller;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author jenshadlich@googlemail.com
 */
public class ListAllMyBucketsResultSerializationTest {

    @Test
    public void test() throws JAXBException, IOException {
        ListAllMyBucketsResult response = new ListAllMyBucketsResult(
                new Owner("foo", "bar"),
                Collections.singletonList(new ListAllMyBucketsResult.BucketsEntry(
                        "test",
                        Instant.parse("2006-02-03T16:45:09.001Z"))));

        final String responseXml = JaxbMarshaller.marshall(response);

        System.out.println(responseXml);

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListAllMyBucketsResult xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">\n" +
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
                "</ListAllMyBucketsResult>";

        assertEquals(expected, responseXml);
    }
}
