package com.codesmashers.decentrabox.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesmashers.decentrabox.model.dto.response.ApiResponseDto;
import com.codesmashers.decentrabox.service.UserService;

@RestController
@RequestMapping("/app/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("info/")
    public ResponseEntity<ApiResponseDto<?>> getLoggedInUserInfo() {
        return userService.getLoggedInUserInfo();
    }

}
