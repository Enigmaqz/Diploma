package ru.netology.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dto.FileListResponse;
import ru.netology.service.StorageService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class StorageController {

    private final StorageService storageService;


    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String filename,
                                        MultipartFile file) throws IOException {
        storageService.uploadFile(authToken, filename, file);
        return new ResponseEntity<>("Success upload", HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String filename) {
        storageService.deleteFile(authToken, filename);
        return new ResponseEntity<>("Success delete", HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<?> downloadFile(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("filename") String filename) {
        byte[] file = storageService.downloadFile(authToken, filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @PutMapping("/file")
    public ResponseEntity<?> editFileName(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("filename") String filename,
                                          @RequestBody Map<String, String> fileNameRequest) {
        storageService.editFileName(authToken, filename, fileNameRequest.get("filename"));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllFiles(@RequestHeader("auth-token") String authToken,
                                         @RequestParam("limit") Integer limit) {
        List<FileListResponse> rp = storageService.getFiles(authToken, limit);
        return new ResponseEntity<>(rp, HttpStatus.OK);
    }
}