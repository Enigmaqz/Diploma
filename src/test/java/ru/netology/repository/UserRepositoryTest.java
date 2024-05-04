package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.netology.entity.User;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    public static final String TEST_LOGIN_1 = "login@test.com";
    public static final String TEST_LOGIN_2 = "nigol@test.com";
    public static final String TEST_PASSWORD = "test";

    @Autowired
    private UserRepository testUserRepository;

    @BeforeEach
    void setUp() {
        User user = new User(TEST_LOGIN_1, TEST_PASSWORD);
        testUserRepository.save(user);
    }

    @Test
    void findUserByLog() {
        assertEquals(TEST_LOGIN_1, testUserRepository.findUserByLogin(TEST_LOGIN_1).getLogin());
    }

    @Test
    void notFindNotUserByLogin() {
        assertNull(testUserRepository.findUserByLogin(TEST_LOGIN_2));
    }
}