package com.mondari.storage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author limondar
 * @date 2022/4/16
 */
@Component
public class StorageServiceInitRunner implements CommandLineRunner {
    private final StorageService storageService;

    public StorageServiceInitRunner(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void run(String... args) throws Exception {
        storageService.clear();
        storageService.init();
    }
}
