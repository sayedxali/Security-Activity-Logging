package com.seyed.ali.controller;


import com.seyed.ali.model.entity.LogUser;
import com.seyed.ali.model.response.Result;
import com.seyed.ali.service.interfaces.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.seyed.ali.model.response.StatusCode.SUCCESS;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.endpoint.base-url}/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Result> registerUser(@Valid @RequestBody LogUser logUser) {
        return new ResponseEntity<>(
                new Result(
                        true,
                        CREATED.value(),
                        "User registration successful.",
                        this.authenticationService.registerUser(logUser)
                ),
                CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(Authentication authentication) {
        log.debug("Authenticated user: '{}'", authentication.getName());
        return new ResponseEntity<>(new Result(
                true,
                SUCCESS,
                "User Info and JSON Web Token.",
                this.authenticationService.createLoginInfo(authentication)
        ),
                OK
        );
    }

}
