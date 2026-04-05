package com.codesmashers.decentrabox.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codesmashers.decentrabox.model.dto.response.ApiResponseDto;
import com.codesmashers.decentrabox.service.file.FileService;

@RestController
@RequestMapping("/app/user/file/")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponseDto<?>> uploadFile(@RequestParam("file") MultipartFile file) {
        return fileService.uploadFile(file);

    }

}
