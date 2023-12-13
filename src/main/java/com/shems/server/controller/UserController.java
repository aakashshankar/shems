package com.shems.server.controller;

import com.shems.server.converter.UserToUserResponseConverter;
import com.shems.server.dto.request.UserRequest;
import com.shems.server.dto.response.UserResponse;
import com.shems.server.service.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserService userService;

    @Inject
    private UserToUserResponseConverter converter;

    @PostMapping("/register")
    ResponseEntity<UserResponse> register(@RequestBody @Valid  UserRequest request) {
        LOGGER.info("Registering user with email: {}", request.getEmail());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(converter.convert(userService.register(request)));
    }

    @GetMapping("/me")
    String user() {
        return "Hello World!";
    }
}
