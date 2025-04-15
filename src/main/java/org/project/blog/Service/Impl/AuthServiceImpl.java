package org.project.blog.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.Enum.ROLE;
import org.project.blog.Constant.Enum.TOKENTYPE;
import org.project.blog.Entity.Token;
import org.project.blog.Entity.User;
import org.project.blog.Mapper.AuthMapper;
import org.project.blog.Mapper.UserMapper;
import org.project.blog.Payload.Request.AuthRequest;
import org.project.blog.Payload.Request.RefreshRequest;
import org.project.blog.Payload.Request.UserRequest;
import org.project.blog.Payload.Response.AuthResponse;
import org.project.blog.Payload.Response.RefreshResponse;
import org.project.blog.Payload.Response.UserResponse;
import org.project.blog.Repository.TokenRepository;
import org.project.blog.Repository.UserRepository;
import org.project.blog.Service.AuthService;
import org.project.blog.Service.BaseJwtService;
import org.project.blog.Service.JwtServiceFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    // jwt factory
    private final JwtServiceFactory jwtServiceFactory;
    //Repository
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    //Encoder
    private final PasswordEncoder encoder;
    //Mapper
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    //Authentication manager
    private final AuthenticationManager authenticationManager;
    //Service
    private final UserDetailsService userDetailsService;

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
        BaseJwtService jwtService = jwtServiceFactory.getService(TOKENTYPE.ACCESS_TOKEN);
        BaseJwtService refreshService = jwtServiceFactory.getService(TOKENTYPE.REFRESH_TOKEN);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        String accessToken, refreshToken;
        Token token;
        User user;
        if (authentication.isAuthenticated()) {
            user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(() -> new RuntimeException("This need to change")); //Change this latter
            accessToken = jwtService.generateToken(authRequest.getUsername());
            refreshToken = refreshService.generateToken(authRequest.getUsername());
            AuthResponse response = authMapper.userToAuthResponse(user);
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);

            //Create token
            token = new Token();
            token.setUser(user);
            token.setValue(refreshToken);
            token.setRevoked(false);
            token.setCreate_at(new Date());
            tokenRepository.save(token);

            return response;
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Override
    public RefreshResponse refreshToken(RefreshRequest refreshRequest) {
        BaseJwtService refreshService = jwtServiceFactory.getService(TOKENTYPE.REFRESH_TOKEN);
        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshRequest.getUsername());
        // Validate the refresh token
        if (!refreshService.validateToken(refreshRequest.getRefreshToken(), userDetails)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // Extract username from the refresh token
        String username = refreshService.extractUsername(refreshRequest.getRefreshToken());
        Optional<List<Token>> tokens = tokenRepository.findByUserUsername(username);
        if (tokens.isEmpty()) {
            throw new RuntimeException("ok this is for later");
        } else {
            for (Token token : tokens.get()) {
                if (token.getValue().equals(refreshRequest.getRefreshToken()) && !token.isRevoked()) {
                    break;
                }
                throw new RuntimeException("ok this is for later");
            }
        }
        // Generate a new access token
        BaseJwtService accessTokenService = jwtServiceFactory.getService(TOKENTYPE.ACCESS_TOKEN);
        String newAccessToken = accessTokenService.generateToken(username);

        // Prepare the response
        RefreshResponse response = new RefreshResponse();
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(refreshRequest.getRefreshToken());

        return response;
    }

    @Override
    public void logout(RefreshRequest refreshRequest) {
        String username = refreshRequest.getUsername();
        Optional<List<Token>> tokens = tokenRepository.findByUserUsername(username);
        if (tokens.isEmpty()) {
            throw new RuntimeException("ok this is for later");
        } else {
            for (Token token : tokens.get()) {
                if (token.getValue().equals(refreshRequest.getRefreshToken()) && !token.isRevoked()) {
                    token.setRevoked(true);
                    tokenRepository.saveAll(tokens.get());
                    return;
                }
            }
        }
    }
}
