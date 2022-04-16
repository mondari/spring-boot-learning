package com.mondari.storage;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * <p>
 *
 * </p>
 *
 * @author limondar
 * @date 2022/4/16
 */
@EnableConfigurationProperties(StorageProperties.class)
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties storageProperties) {
        rootLocation = Paths.get(storageProperties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Cannot init storage", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            throw new StorageException("Cannot store empty file");
        }
        Path target = rootLocation.resolve(Paths.get(file.getOriginalFilename()))
                .normalize().toAbsolutePath();
        if (!target.getParent().equals(rootLocation.toAbsolutePath())) {
            // security check
            throw new StorageException("Cannot store file outside current directory");
        }

        try (InputStream is = file.getInputStream()) {
            Files.copy(is, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    public Stream<Path> list() {
        try {
            return Files.walk(rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(rootLocation::relativize);//只取相对路径
        } catch (IOException e) {
            throw new StorageException("Failed to list files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        Path file = load(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Cannot load file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Cannot load file: " + filename, e);

        }
    }

    @Override
    public void clear() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
