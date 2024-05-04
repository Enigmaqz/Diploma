package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


public class AuthorizationRepositoryTest {
    public static final String AUTH_TOKEN_1 = "Test_Token";
    public static final String AUTH_USERNAME_1 = "Test_Username";
    public static final String AUTH_TOKEN_2 = "Test_Token_2";

    @Autowired
    private AuthorizationRepository testAuthorizationRepository;

    @BeforeEach
    void setUp() {
        testAuthorizationRepository = new AuthorizationRepository();
        testAuthorizationRepository.putToken(AUTH_TOKEN_1, AUTH_USERNAME_1);
    }


    @Test
    void getUserByToken() {
        testAuthorizationRepository.putToken(AUTH_TOKEN_1, AUTH_USERNAME_1);
        assertEquals(AUTH_USERNAME_1, testAuthorizationRepository.getUsernameByToken(AUTH_TOKEN_1));
        assertNull(testAuthorizationRepository.getUsernameByToken(AUTH_TOKEN_2));
    }

    @Test
    void deleteAuthUserByToken() {
        assertNotNull(testAuthorizationRepository.getUsernameByToken(AUTH_TOKEN_1));

        testAuthorizationRepository.removeUserByToken(AUTH_TOKEN_1);

        assertNull(testAuthorizationRepository.getUsernameByToken(AUTH_TOKEN_1));
    }
}

