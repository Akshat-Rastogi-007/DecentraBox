package com.codesmashers.decentrabox.service;

import java.util.Collections;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.codesmashers.decentrabox.model.User;
import com.codesmashers.decentrabox.model.dto.UserRequestDto;
import com.codesmashers.decentrabox.model.dto.response.ApiResponseDto;
import com.codesmashers.decentrabox.model.dto.response.UserResponseDto;
import com.codesmashers.decentrabox.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ResponseEntity<ApiResponseDto<?>> registerUser(UserRequestDto dto) {

        if (userCheck(dto.getEmail()))
            return new ResponseEntity<>(
                    new ApiResponseDto<T>(Collections.EMPTY_MAP, "Email already assosiated with another account",
                            HttpStatus.BAD_REQUEST),
                    HttpStatus.BAD_REQUEST);

        User user = modelMapper.map(dto, User.class);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);

        UserResponseDto mappedObj = modelMapper.map(user, UserResponseDto.class);

        return new ResponseEntity<>(new ApiResponseDto<>(
                mappedObj,
                "User registered successfully",
                HttpStatus.CREATED), HttpStatus.CREATED);
    }

    private boolean userCheck(String email) {

        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();

    }

}
