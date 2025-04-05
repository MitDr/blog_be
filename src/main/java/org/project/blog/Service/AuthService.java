package org.project.blog.Service;

import org.project.blog.Payload.Request.AuthRequest;
import org.project.blog.Payload.Request.RefreshRequest;
import org.project.blog.Payload.Request.UserRequest;
import org.project.blog.Payload.Response.AuthResponse;
import org.project.blog.Payload.Response.RefreshResponse;
import org.project.blog.Payload.Response.UserResponse;

public interface AuthService {

    UserResponse register(UserRequest userRequest);

    AuthResponse login(AuthRequest authRequest);

    RefreshResponse refreshToken(RefreshRequest refreshRequest);
}
