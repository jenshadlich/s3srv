package de.jeha.s3srv.api;

import de.jeha.s3srv.jaxb.JaxbMarshaller;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * @author jenshadlich@googlemail.com
 */
public class ListBucketResultSerializationTest {

    @Test
    public void test() throws JAXBException, IOException {
        ListBucketResult response = new ListBucketResult(
                "test-bucket",
                1,
                1000,
                Collections.singletonList(new ListBucketResult.ContentsEntry(
                        "test-object",
                        Instant.parse("2009-10-12T17:50:30.001Z"),
                        "\"fba9dede5f27731c9771645a39863328\"",
                        434234)));

        final String responseXml = JaxbMarshaller.marshall(response);

        System.out.println(responseXml);

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListBucketResult xmlns=\"http://s3.amazonaws.com/doc/2006-03-01\">\n" +
                "    <Name>test-bucket</Name>\n" +
                "    <KeyCount>1</KeyCount>\n" +
                "    <MaxKeys>1000</MaxKeys>\n" +
                "    <Contents>\n" +
                "        <Key>test-object</Key>\n" +
                "        <LastModified>2009-10-12T17:50:30.001Z</LastModified>\n" +
                "        <ETag>\"fba9dede5f27731c9771645a39863328\"</ETag>\n" +
                "        <Size>434234</Size>\n" +
                "        <StorageClass>STANDARD</StorageClass>\n" +
                "    </Contents>\n" +
                "</ListBucketResult>";

        assertEquals(expected, responseXml);
    }
}
