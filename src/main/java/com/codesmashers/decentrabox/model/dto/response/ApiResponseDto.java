package com.codesmashers.decentrabox.model.dto.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDto<T> {

    private T data;
    private String message;
    private HttpStatus status;

}
