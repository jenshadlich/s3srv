package de.jeha.s3srv.api;

import de.jeha.s3srv.xml.InstantXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ListAllMyBucketsResult")
public class ListAllMyBucketsResult {

    @XmlElement(name = "Owner")
    private Owner owner;

    @XmlElementWrapper(name = "Buckets")
    @XmlElement(name = "Bucket")
    private List<BucketsEntry> buckets = new ArrayList<>();

    public ListAllMyBucketsResult() {
    }

    public ListAllMyBucketsResult(Owner owner, List<BucketsEntry> entries) {
        this.owner = owner;
        buckets.addAll(entries);
    }

    public Owner getOwner() {
        return owner;
    }

    public List<BucketsEntry> getBuckets() {
        return buckets;
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
