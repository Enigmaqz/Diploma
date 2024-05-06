package ru.netology.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.dto.AuthorizationRequest;
import ru.netology.dto.AuthorizationResponse;
import ru.netology.service.AuthorizationService;


@RestController
@AllArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorizationResponse> login(@RequestBody AuthorizationRequest authorizationRequest) {
        AuthorizationResponse response = authorizationService.login(authorizationRequest);
        if (response.getAuthToken() == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authorizationService.logout(authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}