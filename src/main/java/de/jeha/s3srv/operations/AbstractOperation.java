package de.jeha.s3srv.operations;

import de.jeha.s3srv.storage.StorageBackend;

/**
 * @author jenshadlich@googlemail.com
 */
public abstract class AbstractOperation {

    private final StorageBackend storageBackend;

    protected AbstractOperation(StorageBackend storageBackend) {
        this.storageBackend = storageBackend;
    }

    public StorageBackend getStorageBackend() {
        return storageBackend;
    }

}
