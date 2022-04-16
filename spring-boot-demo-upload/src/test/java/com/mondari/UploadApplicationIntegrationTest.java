package com.mondari;

import com.mondari.storage.StorageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *
 * </p>
 *
 * @author limondar
 * @date 2022/4/16
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadApplicationIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private StorageService storageService;

    @Test
    void testUpload() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        // TODO 不能用 ByteArrayResource，想想为何？
        // Resource resource = new ByteArrayResource("Hello".getBytes(StandardCharsets.UTF_8));
        Resource resource = new ClassPathResource("upload.txt");
        map.add("file", resource);
        ResponseEntity<String> response = testRestTemplate.postForEntity("/upload", map, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).contains("OK");

        BDDMockito.then(storageService).should().store(BDDMockito.any(MultipartFile.class));
    }

    @Test
    void testDownload() {
        ClassPathResource classPathResource = new ClassPathResource("upload.txt");
        BDDMockito.given(storageService.loadAsResource("upload.txt")).willReturn(classPathResource);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/files/upload.txt", String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).contains("Spring Framework");
    }
}
