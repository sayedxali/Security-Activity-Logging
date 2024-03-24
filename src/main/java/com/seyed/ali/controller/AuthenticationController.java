package com.seyed.ali.controller;

import com.seyed.ali.model.entity.LogUser;
import com.seyed.ali.model.response.Result;
import com.seyed.ali.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

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

}
