package de.jeha.s3srv.xml;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class JaxbMarshaller {

    private static Marshaller MARSHALLER = null;
    private static Unmarshaller UNMARSHALLER = null;

    static {
        try {
            MARSHALLER = JaxbContextHolder.getContext().createMarshaller();
            MARSHALLER.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            MARSHALLER.setProperty(Marshaller.JAXB_FRAGMENT, true);
        } catch (JAXBException e) {
            throw new RuntimeException("Unable to initialize JAXB Marshaller", e);
        }
        try {
            UNMARSHALLER = JaxbContextHolder.getContext().createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException("Unable to initialize JAXB Unmarshaller", e);
        }
    }

    public synchronized static String marshall(Object object) throws JAXBException, IOException {
        StringWriter stringWriter = new StringWriter();
        stringWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stringWriter.append("\n");

        MARSHALLER.marshal(object, stringWriter);
        stringWriter.close();

        return stringWriter.toString();
    }

    public synchronized static <T> T unmarshall(String xml, Class<T> clazz) throws JAXBException, IOException {
        return UNMARSHALLER.unmarshal(new StreamSource(new StringReader(xml)), clazz).getValue();
    }

}
