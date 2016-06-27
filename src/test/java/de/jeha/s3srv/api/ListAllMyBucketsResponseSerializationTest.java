package de.jeha.s3srv.api;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * @author jenshadlich@googlemail.com
 */
public class ListAllMyBucketsResponseSerializationTest {

    @Test
    public void test() throws JAXBException, IOException {
        ListAllMyBucketsResponse response = new ListAllMyBucketsResponse(
                new ListAllMyBucketsResponse.Owner("foo", "bar"),
                new ListAllMyBucketsResponse.Entry("test", Instant.parse("2006-02-03T16:45:09.001Z")));

        JAXBContext jaxbContext = JAXBContext.newInstance(ListAllMyBucketsResponse.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        StringWriter stringWriter = new StringWriter();
        stringWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stringWriter.append(System.lineSeparator());
        marshaller.marshal(response, stringWriter);
        stringWriter.close();

        System.out.println(stringWriter.toString());

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

        assertEquals(expected, stringWriter.toString());
    }
}
