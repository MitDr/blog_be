package org.project.blog.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.Enum.ROLE;
import org.project.blog.Entity.User;
import org.project.blog.Mapper.AuthMapper;
import org.project.blog.Mapper.UserMapper;
import org.project.blog.Payload.Request.AuthRequest;
import org.project.blog.Payload.Request.RefreshRequest;
import org.project.blog.Payload.Request.UserRequest;
import org.project.blog.Payload.Response.AuthResponse;
import org.project.blog.Payload.Response.RefreshResponse;
import org.project.blog.Payload.Response.UserResponse;
import org.project.blog.Repository.UserRepository;
import org.project.blog.Service.AuthService;
import org.project.blog.Service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse register(UserRequest userRequest) {
        User user = authMapper.userRequestToUser(userRequest);
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user.setRole(ROLE.ROLE_USER);
        userRepository.save(user);

        return userMapper.entityToResponse(user);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        String accessToken, refreshToken;
        User user;
        System.out.println("TEST TEST");
        if (authentication.isAuthenticated()) {
            user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(() -> new RuntimeException("This need to change")); //Change this latter
            System.out.println(user);
            accessToken = jwtService.generateToken(authRequest.getUsername());
//            refreshToken = jwtService.generateRefreshToken(authRequest.getUsername()); //Refresh token perform latter
//            user.setRefreshToken(refreshToken);

            AuthResponse response = authMapper.userToAuthResponse(user);
            response.setAccessToken(accessToken);
            System.out.println(response);
            return response;
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Override
    public RefreshResponse refreshToken(RefreshRequest refreshRequest) {
        //Logic refresh Token
        return null;
    }
}
