package com.example.bikegarage.service;

import com.example.bikegarage.model.File;
import com.example.bikegarage.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileService {
    //repository injection
    @Value("${my.upload_location}")
    private Path fileStoragePath;
    private final String fileStorageLocation;
    private final FileRepository fileRepository;

    public FileService(@Value("${my.upload_location}") String fileStorageLocation, FileRepository fileRepository) {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
        this.fileRepository = fileRepository;
    }

    public String storeFile(MultipartFile file, String url) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }
        fileRepository.save(new File(fileName, file.getContentType(), url));
        return fileName;
    }

    public Resource downloadFile(String fileName) {
        Path filePath = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }
}
