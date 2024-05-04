package ru.netology.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.netology.entity.File;
import ru.netology.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StorageRepositoryTest {

    public static final String TEST_LOGIN = "login@test.com";
    public static final String TEST_PASSWORD = "test";
    public static final String TEST_FILENAME = "testFileName";
    public static final String TEST_NEWNAME = "testNewName";

    public static final User TEST_USER = new User(TEST_LOGIN, TEST_PASSWORD);
    public static final File TEST_FILE = new File(TEST_FILENAME, Long.valueOf(1000L), "TEST".getBytes(), TEST_USER);

    @Autowired
    private StorageRepository testStorageRepository;
    @Autowired
    private UserRepository testUserRepository;


    @BeforeEach
    void setUp() {
        testUserRepository.save(TEST_USER);
        testStorageRepository.save(TEST_FILE);
    }



    @Test
    void findByUserAndFilename() {
        assertEquals(TEST_FILE, testStorageRepository.findByUserAndFilename(TEST_USER, TEST_FILENAME));
    }

    @Test
    void removeByUserAndFilename() {
        assertNotNull(testStorageRepository.findByUserAndFilename(TEST_USER, TEST_FILENAME));
        testStorageRepository.removeByUserAndFilename(TEST_USER, TEST_FILENAME);
        assertNull(testStorageRepository.findByUserAndFilename(TEST_USER, TEST_FILENAME));
    }


    @Test
    void findAllByUser() {

        List<File> fileList = new ArrayList<>();
        fileList.add(TEST_FILE);
        assertEquals(fileList, testStorageRepository.findAllByUser(TEST_USER));
    }

    @Test
    void editFileNameByUser() {

        testStorageRepository.editFileNameByUser(TEST_USER, TEST_FILENAME, TEST_NEWNAME);

        assertNull(testStorageRepository.findByUserAndFilename(TEST_USER, TEST_FILENAME));
        assertNotNull(testStorageRepository.findByUserAndFilename(TEST_USER, TEST_NEWNAME));
    }


}
