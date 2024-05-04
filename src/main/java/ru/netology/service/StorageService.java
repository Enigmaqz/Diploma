package ru.netology.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dto.FileListResponse;
import ru.netology.entity.File;
import ru.netology.entity.User;
import ru.netology.exception.EmptyFileNameException;
import ru.netology.exception.FileNotExistException;
import ru.netology.exception.UploadFileException;
import ru.netology.repository.StorageRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class StorageService {

    private final StorageRepository storageRepository;
    private final UserService userService;
    private final AuthorizationService authorizationService;

    public void uploadFile(String authToken, String filename, MultipartFile multipartFile) throws IOException {
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        User user = userService.getUserByToken(authToken);
        try {
            File uploadFile = new File(filename, multipartFile.getSize(), multipartFile.getBytes(), user);
            storageRepository.save(uploadFile);
            log.info("uploadFile: {} ", uploadFile.getFilename());
        } catch (IOException e) {
            log.error("Upload File error");
            throw new UploadFileException();

        }
    }


    public File getFile(String authToken, String filename) {
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        User user = userService.getUserByToken(authToken);
        File file = storageRepository.findByUserAndFilename(user, filename);
        if (file == null) {
            log.error("Search File error");
            throw new FileNotExistException();
        }
        return file;
    }

    public byte[] downloadFile(String authToken, String filename) {
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        File file = getFile(authToken, filename);
        byte[] fileContent = file.getContent();
        log.info("Downloaded file {}", file.getFilename());
        return fileContent;
    }

    public void deleteFile(String authToken, String filename) {
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        File file = getFile(authToken, filename);
        //storageRepository.delete(file);
        User user = userService.getUserByToken(authToken);
        storageRepository.removeByUserAndFilename(user, filename);
        log.info("Deleted file {}", file.getFilename());
    }

    public void editFileName(String authToken, String filename, String newFileName) {
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        User user = userService.getUserByToken(authToken);
        File file = getFile(authToken, filename);
        if (newFileName != null) {
            storageRepository.editFileNameByUser(user, filename, newFileName);
            log.info("User {} edit file {}. New filename {}", user.getLogin(), filename, newFileName);
        } else {
            throw new EmptyFileNameException();
        }
    }

    public List<FileListResponse> getFiles(String authToken, Integer limit) {
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        User user = userService.getUserByToken(authToken);
        List<File> fileList = storageRepository.findAllByUser(user);
        log.info("User {} responsed all files", user.getLogin());
        return fileList.stream().map(f -> new FileListResponse(f.getFilename(), f.getSize()))
                .limit(limit)
                .collect(Collectors.toList());
    }


}
