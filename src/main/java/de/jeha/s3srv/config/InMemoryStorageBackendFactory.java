package de.jeha.s3srv.config;

import com.fasterxml.jackson.annotation.JsonTypeName;
import de.jeha.s3srv.common.security.Credentials;
import de.jeha.s3srv.storage.StorageBackend;
import de.jeha.s3srv.storage.backends.InMemoryStorageBackend;

/**
 * @author jenshadlich@googlemail.com
 */
@JsonTypeName("in-memory")
public class InMemoryStorageBackendFactory implements StorageBackendFactory {

    private transient InMemoryStorageBackend storageBackend;

    @Override
    public StorageBackend build(Credentials credentials) {
        return new InMemoryStorageBackend(credentials);
    }

}