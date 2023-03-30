package de.jeha.s3srv.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Error")
public class ErrorResponse {

    @XmlElement(name = "Code")
    private String code;

    @XmlElement(name = "Message")
    private String message;

    @XmlElement(name = "Resource")
    private String resource;

    @XmlElement(name = "RequestId")
    private String requestId;

    public ErrorResponse() {
    }

    public ErrorResponse(String code, String message, String resource, String requestId) {
        this.code = code;
        this.message = message;
        this.resource = resource;
        this.requestId = requestId;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getResource() {
        return resource;
    }

    public String getRequestId() {
        return requestId;
    }

}
