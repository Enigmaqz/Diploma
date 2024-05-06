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

    public void uploadFile(File uploadFile) {
            storageRepository.save(uploadFile);
            log.info("uploadFile: {} ", uploadFile.getFilename());
    }


    public File getFile(User user, String filename) {
        File file = storageRepository.findByUserAndFilename(user, filename);
        if (file == null) {
            log.error("Search File error");
            throw new FileNotExistException();
        }
        return file;
    }

    public File downloadFile(User user, String filename) {
        File file = getFile(user, filename);
        log.info("Downloaded file {}", file.getFilename());
        return file;
    }

    public void deleteFile(User user, String filename) {
        storageRepository.removeByUserAndFilename(user, filename);
        log.info("Deleted file {}", filename);
    }

    public void editFileName(User user, String filename, String newFileName) {
        File file = getFile(user, filename);
        if (newFileName != null) {
            storageRepository.editFileNameByUser(user, filename, newFileName);
            log.info("User {} edit file {}. New filename {}", user.getLogin(), filename, newFileName);
        } else {
            throw new EmptyFileNameException();
        }
    }

    public List<FileListResponse> getFiles(User user, Integer limit) {
        List<File> fileList = storageRepository.findAllByUser(user);
        log.info("User {} responsed all files", user.getLogin());
        return fileList.stream().map(f -> new FileListResponse(f.getFilename(), f.getSize()))
                .limit(limit)
                .collect(Collectors.toList());
    }


}
