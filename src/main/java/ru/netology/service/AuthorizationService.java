package ru.netology.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.netology.dto.AuthorizationRequest;
import ru.netology.dto.AuthorizationResponse;
import ru.netology.entity.User;
import ru.netology.exception.BadCredentialsException;
import ru.netology.jwt.JWTUtil;
import ru.netology.repository.AuthorizationRepository;
import ru.netology.repository.UserRepository;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;


    public AuthorizationResponse login(AuthorizationRequest authorizationRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authorizationRequest.getLogin(),
                    authorizationRequest.getPassword()));

        } catch (BadCredentialsException e) {
            log.error("Bad credentials error");
            throw new BadCredentialsException();
        }
        User user = userRepository.findUserByLogin(authorizationRequest.getLogin());
        String token = jwtUtil.generateToken(user.getLogin());
        authorizationRepository.putToken(token, user.getLogin());
        log.info("{} login", user.getLogin());
        return new AuthorizationResponse(token);
    }

    public void logout(String authToken) {
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        final String username = authorizationRepository.getUserByToken(authToken);
        log.info("{} logout", username);
        authorizationRepository.removeUserByToken(authToken);

    }


}