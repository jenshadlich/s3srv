package de.jeha.s3srv.jaxb;

import de.jeha.s3srv.api.ListAllMyBucketsResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * @author jenshadlich@googlemail.com
 */
class JaxbContextHolder {

    private static JAXBContext JAXB_CONTEXT = null;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(ListAllMyBucketsResponse.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Unable to initialize JAXB Context", e);
        }
    }

    public static JAXBContext getContext() {
        return JAXB_CONTEXT;
    }

}
