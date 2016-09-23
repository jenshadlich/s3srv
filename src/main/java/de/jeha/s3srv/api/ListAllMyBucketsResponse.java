package de.jeha.s3srv.api;

import de.jeha.s3srv.xml.InstantXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jenshadlich@googlemail.com
 */
@XmlRootElement(name = "ListAllMyBucketsResponse")
public class ListAllMyBucketsResponse {

    @XmlElement(name = "Owner")
    private Owner owner;

    @XmlElementWrapper(name = "Buckets")
    @XmlElement(name = "Bucket")
    private List<BucketsEntry> buckets = new ArrayList<>();

    public ListAllMyBucketsResponse() {
    }

    public ListAllMyBucketsResponse(Owner owner, List<BucketsEntry> entries) {
        this.owner = owner;
        buckets.addAll(entries);
    }

    public Owner getOwner() {
        return owner;
    }

    public List<BucketsEntry> getBuckets() {
        return buckets;
    }

    public static class Owner {

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

    public static class BucketsEntry {

        @XmlElement(name = "Name")
        private String name;

        @XmlJavaTypeAdapter(InstantXmlAdapter.class)
        @XmlElement(name = "CreationDate")
        private Instant creationDate;

        public BucketsEntry(String name, Instant creationDate) {
            this.name = name;
            this.creationDate = creationDate;
        }

    }

}
