package ru.netology.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.entity.File;
import ru.netology.entity.User;
import ru.netology.repository.StorageRepository;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StorageServiceTest {

    public static final String TEST_FILENAME = "test_filename";
    public static final MultipartFile TEST_MULTIPART_FILE = new MockMultipartFile(TEST_FILENAME, TEST_FILENAME.getBytes());
    public static final String TEST_LOGIN = "login@test.com";
    public static final String TEST_PASSWORD = "test";
    public static final String TEST_TOKEN = "Test_Token";
    public static final User TEST_USER = new User(TEST_LOGIN, TEST_PASSWORD);
    @InjectMocks
    private StorageService storageService;
    @Mock
    private StorageRepository storageRepository;
    @Mock
    private UserService userService;

    @Test
    void uploadFile() throws IOException {

        File testFile = new File(TEST_FILENAME, TEST_MULTIPART_FILE.getSize(), TEST_MULTIPART_FILE.getBytes(), TEST_USER);

        //Mockito.when(userService.getUserByToken(TEST_TOKEN)).thenReturn(TEST_USER);
        Mockito.when(storageRepository.save(testFile)).thenReturn(testFile);

        assertDoesNotThrow(() -> storageService.uploadFile(testFile));

    }

    @Test
    void getFile() throws IOException {
        File testFile = new File(TEST_FILENAME, TEST_MULTIPART_FILE.getSize(), TEST_MULTIPART_FILE.getBytes(), TEST_USER);
        Mockito.when(storageRepository.findByUserAndFilename(TEST_USER, TEST_FILENAME)).thenReturn(testFile);

        assertEquals(testFile, storageService.getFile(TEST_USER, TEST_FILENAME));

    }

    @Test
    void downloadFile() throws IOException {
        File testFile = new File(TEST_FILENAME, TEST_MULTIPART_FILE.getSize(), TEST_MULTIPART_FILE.getBytes(), TEST_USER);


        Mockito.when(storageRepository.findByUserAndFilename(TEST_USER, TEST_FILENAME)).thenReturn(testFile);

        assertEquals(testFile, storageService.downloadFile(TEST_USER, TEST_FILENAME));

    }
}