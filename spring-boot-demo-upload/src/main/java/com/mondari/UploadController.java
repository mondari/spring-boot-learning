package com.mondari;

import com.mondari.storage.StorageException;
import com.mondari.storage.StorageFileNotFoundException;
import com.mondari.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author limondar
 * @date 2022/4/16
 */
@RestController
public class UploadController {

    private final StorageService storageService;

    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) {
        storageService.store(file);
        return Collections.singletonMap("success", "OK");
    }

    @GetMapping("/files")
    public List<String> listUploadedFiles() {
        return storageService.list().map(path ->
                // 将文件在文件系统的URI转成Controller的映射以便能够下载
                MvcUriComponentsBuilder.fromMethodName(UploadController.class, "download",
                        path.getFileName().toString()).build().toUriString()).collect(Collectors.toList());
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        // 将文件转成Resource才能下载
        Resource resource = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }


    @ExceptionHandler(StorageException.class)
    public Map<String, Object> handleException(StorageException e) {
        return Collections.singletonMap("error", e.getMessage());
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<Object> handleException(StorageFileNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
