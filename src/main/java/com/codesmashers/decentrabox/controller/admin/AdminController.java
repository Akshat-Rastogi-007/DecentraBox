package com.codesmashers.decentrabox.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesmashers.decentrabox.model.dto.UserRequestDto;
import com.codesmashers.decentrabox.model.dto.response.ApiResponseDto;
import com.codesmashers.decentrabox.service.admin.AdminService;

@RestController
@RequestMapping("/app/admin/")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register/")
    public ResponseEntity<ApiResponseDto<?>> registerAdmin(@RequestBody UserRequestDto dto) {

        return adminService.regsiterAdmin(dto);

    }

    @GetMapping("/user/{userId}/")
    public ResponseEntity<ApiResponseDto<?>> getUserInfo(@PathVariable String userId) {
        return adminService.getUserInfoById(userId);
    }

    @DeleteMapping("/user/{userId}/")
    public ResponseEntity<ApiResponseDto<?>> deleteUser(@PathVariable String userId) {
        return adminService.deleteUser(userId);
    }

}
