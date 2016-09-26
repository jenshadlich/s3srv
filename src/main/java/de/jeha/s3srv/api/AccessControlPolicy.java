package de.jeha.s3srv.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jenshadlich@googlemail.com
 */
@XmlRootElement(name = "AccessControlPolicy")
public class AccessControlPolicy {

    @XmlElement(name = "Owner")
    private Owner owner;

    @XmlElement(name = "AccessControlList")
    private List<AccessControlPolicy.Grant> acl = new ArrayList<>();

    public AccessControlPolicy() {
    }

    public AccessControlPolicy(Owner owner, List<Grant> acl) {
        this.owner = owner;
        this.acl = acl;
    }

    public static class Grant {

        @XmlElement(name = "Grantee")
        private Grantee grantee;

        @XmlElement(name = "Permission")
        private String permission;

        public Grant(Grantee grantee, String permission) {
            this.grantee = grantee;
            this.permission = permission;
        }
    }

}

