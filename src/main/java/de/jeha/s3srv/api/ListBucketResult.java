package de.jeha.s3srv.api;

import de.jeha.s3srv.xml.InstantXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ListBucketResult")
public class ListBucketResult {

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Prefix")
    private String prefix;

    @XmlElement(name = "KeyCount")
    private Integer keyCount;

    @XmlElement(name = "MaxKeys")
    private Integer maxKeys;

    @XmlElement(name = "Delimiter")
    private String delimiter;

    @XmlElement(name = "IsTruncated")
    private Boolean isTruncated = Boolean.FALSE;

    @XmlElement(name = "Contents")
    private List<ContentsEntry> objects = new ArrayList<>();

    @XmlElement(name = "CommonPrefixes")
    private List<CommonPrefix> commonPrefixes = new ArrayList<>();

    public ListBucketResult() {
    }

    public ListBucketResult(String name, Integer keyCount, Integer maxKeys, List<ContentsEntry> objects) {
        this.name = name;
        this.keyCount = keyCount;
        this.maxKeys = maxKeys;
        this.objects = objects;
    }

    public ListBucketResult(String name, Integer keyCount, Integer maxKeys, List<ContentsEntry> objects, List<CommonPrefix> commonPrefixes) {
        this.name = name;
        this.keyCount = keyCount;
        this.maxKeys = maxKeys;
        this.objects = objects;
        this.commonPrefixes = commonPrefixes;
    }

    public static class ContentsEntry {

        private static final String STORAGE_CLASS_STANDARD = "STANDARD";
        private static final String STORAGE_CLASS_REDUCED_REDUNDANCY = "REDUCED_REDUNDANCY";

        @XmlElement(name = "Key")
        private String key;

        @XmlJavaTypeAdapter(InstantXmlAdapter.class)
        @XmlElement(name = "LastModified")
        private Instant lastModified;

        @XmlElement(name = "ETag")
        private String eTag;

        @XmlElement(name = "Size")
        private Integer size;

        @XmlElement(name = "StorageClass")
        private String storageClass = STORAGE_CLASS_STANDARD;

        public ContentsEntry(String key, Instant lastModified, String eTag, Integer size) {
            this.key = key;
            this.lastModified = lastModified;
            this.eTag = eTag;
            this.size = size;
        }
    }

    public static class CommonPrefix {

        @XmlElement(name = "Prefix")
        private String prefix;

        public CommonPrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
