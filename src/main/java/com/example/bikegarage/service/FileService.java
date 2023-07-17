package com.example.bikegarage.service;

import com.example.bikegarage.model.File;
import com.example.bikegarage.repository.FileRepository;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public String storeFile(MultipartFile file, String url){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }
        fileRepository.save(new File(fileName, file.getContentType(), filePath.toString()));


        return fileName;
    }


}
