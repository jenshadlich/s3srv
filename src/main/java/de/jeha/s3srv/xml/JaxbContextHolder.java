package de.jeha.s3srv.xml;

import de.jeha.s3srv.api.AccessControlPolicy;
import de.jeha.s3srv.api.ErrorResponse;
import de.jeha.s3srv.api.ListAllMyBucketsResult;
import de.jeha.s3srv.api.ListBucketResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

class JaxbContextHolder {

    private static JAXBContext JAXB_CONTEXT = null;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(
                    AccessControlPolicy.class,
                    ErrorResponse.class,
                    ListAllMyBucketsResult.class,
                    ListBucketResult.class
            );
        } catch (JAXBException e) {
            throw new RuntimeException("Unable to initialize JAXB Context", e);
        }
    }

    public static JAXBContext getContext() {
        return JAXB_CONTEXT;
    }

}
