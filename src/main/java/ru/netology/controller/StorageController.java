package ru.netology.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dto.FileListResponse;
import ru.netology.entity.File;
import ru.netology.entity.User;
import ru.netology.service.AuthorizationService;
import ru.netology.service.StorageService;
import ru.netology.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class StorageController {

    private final StorageService storageService;
    private final UserService userService;
    private final AuthorizationService authorizationService;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String filename,
                                        MultipartFile multipartFile) throws IOException {

        String userName = authorizationService.getUsernameByToken(authToken);
        User user = userService.getUserByUsername(userName);

        File uploadFile = new File(filename, multipartFile.getSize(), multipartFile.getBytes(), user);

        storageService.uploadFile(uploadFile);
        return new ResponseEntity<>("Success upload", HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String filename) {

        String userName = authorizationService.getUsernameByToken(authToken);
        User user = userService.getUserByUsername(userName);
        storageService.deleteFile(user, filename);
        return new ResponseEntity<>("Success delete", HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<?> downloadFile(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("filename") String filename) {

        String userName = authorizationService.getUsernameByToken(authToken);
        User user = userService.getUserByUsername(userName);
        byte[] file = storageService.downloadFile(user, filename).getContent();
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @PutMapping("/file")
    public ResponseEntity<?> editFileName(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("filename") String filename,
                                          @RequestBody Map<String, String> fileNameRequest) {

        String userName = authorizationService.getUsernameByToken(authToken);
        User user = userService.getUserByUsername(userName);
        storageService.editFileName(user, filename, fileNameRequest.get("filename"));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllFiles(@RequestHeader("auth-token") String authToken,
                                         @RequestParam("limit") Integer limit) {

        String userName = authorizationService.getUsernameByToken(authToken);
        User user = userService.getUserByUsername(userName);
        List<File> fileList = storageService.getFiles(user, limit);
        List<FileListResponse> rp = fileList.stream().map(f -> new FileListResponse(f.getFilename(), f.getSize()))
                .limit(limit)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rp, HttpStatus.OK);
    }
}