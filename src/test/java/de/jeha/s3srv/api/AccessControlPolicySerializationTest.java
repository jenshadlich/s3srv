package de.jeha.s3srv.api;

import de.jeha.s3srv.common.acl.ACLPermissions;
import de.jeha.s3srv.xml.JaxbMarshaller;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccessControlPolicySerializationTest {

    @Test
    public void test() throws JAXBException, IOException {
        AccessControlPolicy response = new AccessControlPolicy(
                new Owner("foo", "bar"),
                Collections.singletonList(new AccessControlPolicy.Grant(new Grantee("foo", "bar"), ACLPermissions.FULL_CONTROL.name())));

        final String responseXml = JaxbMarshaller.marshall(response);

        System.out.println(responseXml);

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<AccessControlPolicy xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">\n" +
                "    <Owner>\n" +
                "        <ID>foo</ID>\n" +
                "        <DisplayName>bar</DisplayName>\n" +
                "    </Owner>\n" +
                "    <AccessControlList>\n" +
                "        <Grantee>\n" +
//                "        <Grantee xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
//                "                 xsi:type=\"Canonical User\">\n" +
                "            <ID>foo</ID>\n" +
                "            <DisplayName>bar</DisplayName>\n" +
                "        </Grantee>\n" +
                "        <Permission>FULL_CONTROL</Permission>\n" +
                "    </AccessControlList>\n" +
                "</AccessControlPolicy>";

        assertEquals(expected, responseXml);
    }
}
