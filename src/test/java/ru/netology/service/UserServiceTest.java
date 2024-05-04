package ru.netology.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.netology.entity.User;
import ru.netology.exception.UnauthorizedException;
import ru.netology.repository.AuthorizationRepository;
import ru.netology.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    public static final String TEST_USERNAME = "Test_Username";
    public static final String TEST_PASSWORD = "Test_Password";
    public static final User TEST_USER = new User(TEST_USERNAME, TEST_PASSWORD);


    public static final String TEST_TOKEN = "Test_Token";
    public static final String NOT_VALID_TOKEN = "Not_valid_token";

    @InjectMocks
    private UserService testUserService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthorizationRepository authorizationRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(userRepository.findUserByLogin(TEST_USERNAME)).thenReturn(TEST_USER);
        Mockito.when(authorizationRepository.getUsernameByToken(TEST_TOKEN)).thenReturn(TEST_USERNAME);
    }

    @Test
    void loadUserByUsername() {
        assertEquals(TEST_USER.getLogin(), testUserService.loadUserByUsername(TEST_USERNAME).getUsername());
        assertEquals(TEST_USER.getPassword(), testUserService.loadUserByUsername(TEST_USERNAME).getPassword());
    }

    @Test
    void getUserByUsername() {
        assertEquals(TEST_USER, testUserService.getUserByUsername(TEST_USERNAME));
    }

}