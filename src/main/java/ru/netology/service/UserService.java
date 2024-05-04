package ru.netology.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.netology.entity.User;
import ru.netology.exception.UnauthorizedException;
import ru.netology.repository.AuthorizationRepository;
import ru.netology.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorizationRepository authorizationRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login);
        if (user == null) {
            log.error("Unauthorized Exception error");
            throw new UnauthorizedException();
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        log.info("recieved {} by login {}", user.getLogin(), login);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    public User getUserByToken(String authToken) throws RuntimeException {
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        String userName = authorizationRepository.getUserByToken(authToken);
        User user = userRepository.findUserByLogin(userName);
        if (user == null) {
            log.error("Unauthorized Exception error");
            throw new UnauthorizedException();
        }
        return user;
    }


}