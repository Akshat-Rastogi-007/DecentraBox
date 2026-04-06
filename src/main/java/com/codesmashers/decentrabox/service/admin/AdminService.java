package com.codesmashers.decentrabox.service.admin;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codesmashers.decentrabox.exception.ResourceAlreadyExistsException;
import com.codesmashers.decentrabox.exception.ResourceNotFoundException;
import com.codesmashers.decentrabox.model.Role;
import com.codesmashers.decentrabox.model.User;
import com.codesmashers.decentrabox.model.dto.UserRequestDto;
import com.codesmashers.decentrabox.model.dto.response.ApiResponseDto;
import com.codesmashers.decentrabox.model.dto.response.UserResponseDto;
import com.codesmashers.decentrabox.repository.UserRepository;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<ApiResponseDto<?>> regsiterAdmin(UserRequestDto dto) {

        if (adminCheck(dto.getEmail()))
            throw new ResourceAlreadyExistsException("Email already assosiated with another account");

        User user = modelMapper.map(dto, User.class);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.getRoles().add(Role.ROLE_ADMIN);

        userRepository.save(user);

        UserResponseDto mappedObj = modelMapper.map(user, UserResponseDto.class);

        return new ResponseEntity<>(new ApiResponseDto<>(
                mappedObj,
                "Admin registered successfully",
                HttpStatus.CREATED), HttpStatus.CREATED);

    }

    private boolean adminCheck(String email) {

        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();

    }

    public ResponseEntity<ApiResponseDto<?>> getUserInfoById(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

        return buildResponse(userResponseDto, "User info retrieved successfully", HttpStatus.OK);
    }

    public ResponseEntity<ApiResponseDto<?>> deleteUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        userRepository.delete(user);

        return buildResponse(null, "User deleted successfully", HttpStatus.OK);
    }

    private ResponseEntity<ApiResponseDto<?>> buildResponse(Object data, String message, HttpStatus status) {
        return new ResponseEntity<>(new ApiResponseDto<>(data, message, status), status);
    }

}
