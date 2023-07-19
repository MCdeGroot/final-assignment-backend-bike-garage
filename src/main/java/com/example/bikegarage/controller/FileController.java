package com.example.bikegarage.controller;

import com.example.bikegarage.model.File;
import com.example.bikegarage.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

@RestController
@CrossOrigin
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    //    post for single upload
    @PostMapping("upload-file")
    File singleFileUpload(@RequestParam("file") MultipartFile file){

        // next line makes url. example "http://localhost:8080/download-file/id"
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download-file/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        String fileName = fileService.storeFile(file, url);

        return new File(fileName, contentType, url );
    }

    //    get for single download
    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = fileService.downloadFile(fileName);

//        this mediaType decides witch type you accept if you only accept 1 type
//        MediaType contentType = MediaType.IMAGE_JPEG;
//        this is going to accept multiple types
        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

//        for download attachment use next line
//        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename()).body(resource);
//        for showing image in browser
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }
//
//    //    get all names in directory
//    @GetMapping("/download/allNames")
//    List<String> downLoadMultipleFile() {
//
//        return fileStorageService.downLoad();
//
//    }
//
//    //    post for multiple uploads
//    @PostMapping("/multiple/upload")
//    List<FileUploadResponse> multipleUpload(@RequestParam("files") MultipartFile[] files) {
//
//        if(files.length > 7) {
//            throw new RuntimeException("to many files");
//        }
//        List<FileUploadResponse> uploadResponseList = new ArrayList<>();
//        Arrays.stream(files).forEach(file -> {
//            String fileName = fileStorageService.storeFile(file);
//
//            // next line makes url. example "http://localhost:8080/download/naam.jpg"
//            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileName).toUriString();
//
//            String contentType = file.getContentType();
//
//            FileUploadResponse response = new FileUploadResponse(fileName, contentType, url );
//            uploadResponseList.add(response);
//
//        });
//
//        return uploadResponseList;
//
//    }
//
//    @GetMapping("zipDownload")
//    public void zipDownload(@RequestParam("fileName") String[] files, HttpServletResponse response) throws IOException {
//
//        try(ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())){
//            Arrays.stream(files).forEach(file -> {
//                try {
//                    databaseService.createZipEntry(file, zos);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            zos.finish();
//        }
//
//        response.setStatus(200);
//        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=zipfile");
//    }
//
}
