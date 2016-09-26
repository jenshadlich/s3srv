package de.jeha.s3srv.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jenshadlich@googlemail.com
 */
//@XmlRootElement(namespace="http://www.w3.org/2001/XMLSchema-instance")
public class Grantee {

    @XmlElement(name = "ID")
    private String id;

    @XmlElement(name = "DisplayName")
    private String displayName;

    public Grantee() {
    }

    public Grantee(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

}
