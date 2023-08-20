package com.example.bikegarage.service;

import com.example.bikegarage.model.File;
import com.example.bikegarage.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;

    private Path fileStoragePath;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        fileStoragePath = Files.createTempDirectory("test-files");
        fileService = new FileService(fileStoragePath.toString(), fileRepository);
    }

    @Test
    void testStoreFile() {
        MultipartFile multipartFile = new MockMultipartFile("test.txt", "Hello, World!".getBytes());
        String url = "http://example.com/files/";

        String result = fileService.storeFile(multipartFile, url);

        assertNotNull(result);
        verify(fileRepository, times(1)).save(any(File.class));
    }

    @Test
    void testDownloadFile() throws IOException {
        String content = "Test file content";
        Path filePath = Files.createFile(Paths.get(fileStoragePath.toString(), "test.txt"));
        Files.write(filePath, content.getBytes());

        Resource resource = fileService.downloadFile("test.txt");

        assertNotNull(resource);
        assertTrue(resource.exists());
        assertTrue(resource.isReadable());
    }

    @Test
    void testDownloadNonExistentFile() {
        assertThrows(RuntimeException.class, () -> fileService.downloadFile("nonexistent.txt"));
    }
}