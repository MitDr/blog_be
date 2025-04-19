package org.project.blog.Controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.blog.Payload.ClientResponse;
import org.project.blog.Payload.Request.AuthRequest;
import org.project.blog.Payload.Request.RefreshRequest;
import org.project.blog.Payload.Request.UserRequest;
import org.project.blog.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().body(new ClientResponse(authService.register(userRequest), "Register successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        return ResponseEntity.ok().body(new ClientResponse(authService.login(authRequest, request), "Login successfully"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok().body(new ClientResponse(authService.refreshToken(refreshRequest), "Refresh Successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshRequest refreshRequest) {
        authService.logout(refreshRequest);
        return ResponseEntity.ok().body(new ClientResponse(null, "Logout successfully"));
    }
}
