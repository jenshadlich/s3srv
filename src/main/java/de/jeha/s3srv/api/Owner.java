package de.jeha.s3srv.api;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author jenshadlich@googlemail.com
 */
public class Owner {

    @XmlElement(name = "ID")
    private String id;

    @XmlElement(name = "DisplayName")
    private String displayName;

    public Owner() {
    }

    public Owner(String id, String displayName) {
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
