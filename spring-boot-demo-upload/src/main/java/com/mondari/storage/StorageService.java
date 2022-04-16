package com.mondari.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * <p>
 *
 * </p>
 *
 * @author limondar
 * @date 2022/4/16
 */
public interface StorageService {
    void init();

    void store(MultipartFile file);

    Stream<Path> list();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void clear();

}
