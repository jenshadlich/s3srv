package de.jeha.s3srv.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jenshadlich@googlemail.com
 */
@XmlRootElement(name = "ListBucketResult")
public class ListBucketResult {

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "KeyCount")
    private Integer keyCount;

    @XmlElement(name = "MaxKeys")
    private Integer maxKeys;

    @XmlElement(name = "Contents")
    private List<ContentsEntry> objects = new ArrayList<>();

    public ListBucketResult() {
    }

    public ListBucketResult(String name, Integer keyCount, Integer maxKeys, List<ContentsEntry> objects) {
        this.name = name;
        this.keyCount = keyCount;
        this.maxKeys = maxKeys;
        this.objects = objects;
    }

    public static class ContentsEntry {

        @XmlElement(name = "Key")
        private String key;

        public ContentsEntry(String key) {
            this.key = key;
        }

    }

}
