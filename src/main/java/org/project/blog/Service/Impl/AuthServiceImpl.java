package org.project.blog.Service.Impl;

import jakarta.servlet.http.HttpServletRequest;
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
    public AuthResponse login(AuthRequest authRequest, HttpServletRequest request) {

        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        if (tokenRepository.existsByUserAndDeviceIdAndRevokedFalse(user, authRequest.getDeviceId())) {
            throw new RuntimeException("This device is already logged in");
        }

        BaseJwtService jwtService = jwtServiceFactory.getService(TOKENTYPE.ACCESS_TOKEN);
        BaseJwtService refreshService = jwtServiceFactory.getService(TOKENTYPE.REFRESH_TOKEN);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        String accessToken, refreshToken;
        Token token;

        if (authentication.isAuthenticated()) {
            accessToken = jwtService.generateToken(authRequest.getUsername());
            refreshToken = refreshService.generateToken(authRequest.getUsername());
            AuthResponse response = authMapper.userToAuthResponse(user);
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);

            token = new Token();
            token.setUser(user);
            token.setValue(refreshToken);
            token.setRevoked(false);
            token.setDeviceId(authRequest.getDeviceId());
            token.setUserAgent(request.getHeader("User-Agent"));
            tokenRepository.save(token);

            return response;
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Override
    public RefreshResponse refreshToken(RefreshRequest refreshRequest) {
        Token token = tokenRepository.findByValueAndDeviceId(refreshRequest.getRefreshToken(), refreshRequest.getDeviceId()).orElseThrow(() -> new RuntimeException("Token not found"));
        if (token.isRevoked()) {
            throw new RuntimeException("Token is revoked");
        }

        BaseJwtService refreshTokenService = jwtServiceFactory.getService(TOKENTYPE.REFRESH_TOKEN);
        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshRequest.getUsername());
        
        if (!refreshTokenService.isTokenExpired(refreshRequest.getRefreshToken())) {
            throw new RuntimeException("invalid refresh token");
        }
        if (refreshTokenService.isTokenExpired(refreshRequest.getRefreshToken())) {
            throw new RuntimeException("Token is expired");
        }
        String accessToken = jwtServiceFactory.getService(TOKENTYPE.ACCESS_TOKEN).generateToken(userDetails.getUsername());
        return new RefreshResponse(accessToken, refreshRequest.getRefreshToken());
    }

    @Override
    public void logout(RefreshRequest refreshRequest) {
        Optional<Token> token = tokenRepository.findByValueAndDeviceId(refreshRequest.getRefreshToken(), refreshRequest.getDeviceId());
        if (token.isPresent()) {
            token.get().setRevoked(true);
            tokenRepository.save(token.get());
        } else {
            throw new RuntimeException("ok this is for later");
        }
    }
}
