package com.mondari.storage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Random;

class FileSystemStorageServiceTest {

    private final StorageProperties storageProperties = new StorageProperties();
    private FileSystemStorageService storageService;

    @BeforeEach
    void init() {
        // 保证每个单元测试使用不同的存储目录
        storageProperties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
        storageService = new FileSystemStorageService(storageProperties);
        storageService.clear();
        storageService.init();
    }

    @Test
    void loadNonExistent() {
        Assertions.assertThat(storageService.load("not-exist")).doesNotExist();
    }

    @Test
    void loadExistent() {
        storageService.store(new MockMultipartFile("upload", "upload.txt",
                MediaType.TEXT_PLAIN_VALUE, "uploadContent".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertThat(storageService.load("upload.txt")).exists();
    }

    @Test
    void saveRelativePathNotPermitted() {
        org.junit.jupiter.api.Assertions.assertThrows(StorageException.class, () ->
                storageService.store(new MockMultipartFile("upload", "../upload.txt",
                        MediaType.TEXT_PLAIN_VALUE, "uploadContent".getBytes(StandardCharsets.UTF_8)))
        );
    }

    @Test
    void saveAbsolutePathNotPermitted() {
        org.junit.jupiter.api.Assertions.assertThrows(StorageException.class, () ->
                storageService.store(new MockMultipartFile("upload", "/upload.txt",
                        MediaType.TEXT_PLAIN_VALUE, "uploadContent".getBytes(StandardCharsets.UTF_8)))
        );
    }

    @Test
    void savePermitted() {
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                storageService.store(new MockMultipartFile("upload", "foo/../bar.txt",
                        MediaType.TEXT_PLAIN_VALUE, "uploadContent".getBytes(StandardCharsets.UTF_8)))
        );
    }

    @Test
    @EnabledOnOs({OS.LINUX})
    void savePermittedInLinux() {
        // Linux文件系统如Ext4允许文件名中包含“\”字符
        String filename = "\\etc\\passwd";
        storageService.store(new MockMultipartFile(filename, filename,
                MediaType.TEXT_PLAIN_VALUE, "uploadContent".getBytes(StandardCharsets.UTF_8)));
        Assertions.assertThat(storageService.load(filename)).exists();
    }

}