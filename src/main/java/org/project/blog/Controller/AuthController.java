package org.project.blog.Controller;

import lombok.RequiredArgsConstructor;
import org.project.blog.Payload.Request.AuthRequest;
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
        return ResponseEntity.ok().body(authService.register(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        System.out.println(authRequest);
        return ResponseEntity.ok().body(authService.login(authRequest));
    }
}
