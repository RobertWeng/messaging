package com.weng.messaging.controller.app;

import com.weng.messaging.model.dto.response.UserRes;
import com.weng.messaging.security.UserOnly;
import com.weng.messaging.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@Validated
@Slf4j
@Transactional
@UserOnly
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Get Own Account Profile", description = "This API is used to retrieve user's information")
    @GetMapping("/me")
    public ResponseEntity<UserRes> register() {
        return ResponseEntity.ok(userService.findMe());
    }
}