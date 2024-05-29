package com.selfcoder.user.controller;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfcoder.user.form.UserLoginForm;
import com.selfcoder.user.form.UserRegistrationForm;
import com.selfcoder.user.form.UserUpdateForm;
import com.selfcoder.user.service.UserService;
import com.selfcoder.user.util.APIResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<APIResponseDTO> registerUser(@Valid @RequestBody UserRegistrationForm registrationForm) {
        return createResponse(userService.registerUser(registrationForm));
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponseDTO> login(@Valid @RequestBody UserLoginForm loginForm) {
        return createResponse(userService.login(loginForm));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponseDTO> getUserById(@PathVariable("id") final Long id) {
        log.info("Fetching user with id: {}", id);
        return createResponse(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponseDTO> deleteUserById(@PathVariable("id") final Long id) {
        log.info("Deleting user with id: {}", id);
        return createResponse(userService.deleteUserById(Collections.singletonList(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponseDTO> updateUser(@PathVariable("id") final Long id,
            @Valid @RequestBody UserUpdateForm userUpdateForm) {
        log.info("Updating user with id: {}", id);
        return createResponse(userService.updateUser(id, userUpdateForm));
    }

    @GetMapping("/byUserName/{username}")
    public ResponseEntity<APIResponseDTO> getUserByUserName(@PathVariable String username) {
        log.info("Fetching user by username: {}", username);
        return createResponse(userService.getUserByUserName(username));
    }


    private ResponseEntity<APIResponseDTO> createResponse(APIResponseDTO apiResponseDto) {
        return new ResponseEntity<>(apiResponseDto, HttpStatus.valueOf(apiResponseDto.getStatus()));
    }
}
