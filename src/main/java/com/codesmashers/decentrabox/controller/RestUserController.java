package com.codesmashers.decentrabox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codesmashers.decentrabox.model.dto.UserRequestDto;
import com.codesmashers.decentrabox.model.dto.response.ApiResponseDto;
import com.codesmashers.decentrabox.service.UserService;

import jakarta.validation.Valid;

@RestController
public class RestUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<?>> registerUser(@Valid @RequestBody UserRequestDto dto) {
        return userService.registerUser(dto);
    }

}
