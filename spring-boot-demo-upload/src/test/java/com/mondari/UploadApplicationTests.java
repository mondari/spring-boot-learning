package com.mondari;

import com.mondari.storage.StorageFileNotFoundException;
import com.mondari.storage.StorageService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.stream.Stream;

@AutoConfigureMockMvc
@SpringBootTest
class UploadApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageService storageService;

    @Test
    void testUpload() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "upload.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hello".getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(mockMultipartFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("OK"));

        BDDMockito.then(storageService).should().store(mockMultipartFile);
    }

    @Test
    void testListUploadedFiles() throws Exception {
        BDDMockito.given(storageService.list()).willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));
        mockMvc.perform(MockMvcRequestBuilders.get("/files"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.contains("http://localhost/files/first.txt", "http://localhost/files/second.txt")));
    }

    @Test
    void testDownload() throws Exception {
        BDDMockito.given(storageService.loadAsResource("upload.txt")).
                willReturn(new ByteArrayResource("Hello".getBytes(StandardCharsets.UTF_8)));
        mockMvc.perform(MockMvcRequestBuilders.get("/files/upload.txt"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello"));
    }

    @Test
    void testDownloadErrorWhenFileNotFound() throws Exception {
        BDDMockito.given(storageService.loadAsResource("upload.txt")).willThrow(StorageFileNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/files/upload.txt"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
