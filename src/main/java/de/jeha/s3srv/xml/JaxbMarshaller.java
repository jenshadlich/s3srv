package de.jeha.s3srv.xml;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author jenshadlich@googlemail.com
 */
public class JaxbMarshaller {

    private static Marshaller MARSHALLER = null;

    static {
        try {
            MARSHALLER = JaxbContextHolder.getContext().createMarshaller();
            MARSHALLER.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            MARSHALLER.setProperty(Marshaller.JAXB_FRAGMENT, true);
        } catch (JAXBException e) {
            throw new RuntimeException("Unable to initialize JAXB Marshaller", e);
        }
    }

    public static String marshall(Object object) throws JAXBException, IOException {
        StringWriter stringWriter = new StringWriter();
        stringWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stringWriter.append("\n");

        MARSHALLER.marshal(object, stringWriter);
        stringWriter.close();

        return stringWriter.toString();
    }
}
