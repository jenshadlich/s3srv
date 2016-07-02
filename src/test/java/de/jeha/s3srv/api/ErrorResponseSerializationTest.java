package de.jeha.s3srv.api;

import de.jeha.s3srv.common.errors.ErrorCodes;
import de.jeha.s3srv.xml.JaxbMarshaller;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author jenshadlich@googlemail.com
 */
public class ErrorResponseSerializationTest {

    @Test
    public void test() throws JAXBException, IOException {
        ErrorResponse response =
                new ErrorResponse(ErrorCodes.SERVICE_UNAVAILABLE.getCode(), ErrorCodes.SERVICE_UNAVAILABLE.getDescription(), null, null);

        final String responseXml = JaxbMarshaller.marshall(response);

        System.out.println(responseXml);

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Error xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">\n" +
                "    <Code>ServiceUnavailable</Code>\n" +
                "    <Message>Reduce your request rate.</Message>\n" +
                "</Error>";

        assertEquals(expected, responseXml);
    }
}
