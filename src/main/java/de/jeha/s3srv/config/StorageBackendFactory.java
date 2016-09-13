package de.jeha.s3srv.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.jeha.s3srv.common.security.Credentials;
import de.jeha.s3srv.storage.StorageBackend;
import io.dropwizard.jackson.Discoverable;

/**
 * @author jenshadlich@googlemail.com
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface StorageBackendFactory extends Discoverable {

    StorageBackend build(Credentials credentials);

}