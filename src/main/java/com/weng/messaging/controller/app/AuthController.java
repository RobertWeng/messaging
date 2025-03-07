package com.weng.messaging.controller.app;

import com.weng.messaging.model.dto.request.CreateUserReq;
import com.weng.messaging.model.dto.request.LoginReq;
import com.weng.messaging.model.dto.request.RefreshAccessTokenReq;
import com.weng.messaging.model.dto.response.LoginRes;
import com.weng.messaging.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Validated
@Slf4j
@Transactional
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = "Register User Account", description = "This API is used to register user account")
    @PostMapping("/register")
    public ResponseEntity<LoginRes> register(@Valid @RequestBody CreateUserReq req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @Operation(summary = "Login", description = "This API is used to login user account")
    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@Valid @RequestBody LoginReq req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @Operation(summary = "Refresh access token", description = "This API serves the purpose of refresh the access token")
    @PostMapping("/access-token/refresh")
    public ResponseEntity<LoginRes> refreshAccessToken(@Valid @RequestBody RefreshAccessTokenReq req) {
        return ResponseEntity.ok(authService.refreshAccessToken(req));
    }
}