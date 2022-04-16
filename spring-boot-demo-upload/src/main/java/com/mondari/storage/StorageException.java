package com.mondari.storage;

/**
 * <p>
 *
 * </p>
 *
 * @author limondar
 * @date 2022/4/16
 */
public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
