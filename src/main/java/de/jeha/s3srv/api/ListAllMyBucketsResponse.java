package de.jeha.s3srv.api;

import de.jeha.s3srv.jaxb.InstantXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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

    public ListAllMyBucketsResponse(Owner owner, BucketsEntry... entries) {
        this.owner = owner;
        Collections.addAll(buckets, entries);
    }

    public ListAllMyBucketsResponse(Owner owner, List<BucketsEntry> entries) {
        this.owner = owner;
        buckets.addAll(entries);
    }

    public static class Owner {

        @XmlElement(name = "ID")
        private String id;

        @XmlElement(name = "DisplayName")
        private String displayName;

        public Owner(String id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }

    }

    public static class BucketsEntry {

        @XmlElement(name = "Name")
        private String name;

        @XmlElement(name = "CreationDate")
        @XmlJavaTypeAdapter(InstantXmlAdapter.class)
        private Instant creationDate;

        public BucketsEntry(String name, Instant creationDate) {
            this.name = name;
            this.creationDate = creationDate;
        }

    }

}
