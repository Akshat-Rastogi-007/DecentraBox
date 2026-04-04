package com.codesmashers.decentrabox.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codesmashers.decentrabox.exception.ResourceAlreadyExistsException;
import com.codesmashers.decentrabox.exception.UserAuthenticationError;
import com.codesmashers.decentrabox.model.User;
import com.codesmashers.decentrabox.model.dto.LoginDto;
import com.codesmashers.decentrabox.model.dto.UserRequestDto;
import com.codesmashers.decentrabox.model.dto.response.ApiResponseDto;
import com.codesmashers.decentrabox.model.dto.response.UserResponseDto;
import com.codesmashers.decentrabox.repository.UserRepository;
import com.codesmashers.decentrabox.security.jwt.JwtUtil;
import com.codesmashers.decentrabox.security.user.UserDetailsImpl;
import com.codesmashers.decentrabox.security.user.UserDetailsServiceImpl;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ResponseEntity<ApiResponseDto<?>> registerUser(UserRequestDto dto) {

        if (userCheck(dto.getEmail()))
            throw new ResourceAlreadyExistsException("Email already assosiated with another account");

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

    public ResponseEntity<ApiResponseDto<?>> login(LoginDto loginDto) {

        UserDetails userDetails = userDetailsImpl.loadUserByUsername(loginDto.getEmail());

        boolean matches = passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword());

        if (!matches) {
            throw new UserAuthenticationError("Username or password is wrong");
        }

        String jwtToken = jwtUtil.generateToken((UserDetailsImpl) userDetails, 86400000);

        return new ResponseEntity<>(
                new ApiResponseDto<>(jwtToken, "User Loggedin successfully", HttpStatus.OK), HttpStatus.OK);

    }

}
