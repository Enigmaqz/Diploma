package ru.netology.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AuthorizationRepository {

    private final Map<String, String> tokenAndUserName = new ConcurrentHashMap<>();

    public void putToken(String token, String username) {
        tokenAndUserName.put(token, username);
    }

    public String getUsernameByToken(String token) {
        return tokenAndUserName.get(token);
    }

    public void removeUserByToken(String token) {
        tokenAndUserName.remove(token);
    }
}