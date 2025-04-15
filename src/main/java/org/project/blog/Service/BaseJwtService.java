package org.project.blog.Service;

import org.springframework.security.core.userdetails.UserDetails;

public interface BaseJwtService {
    String generateToken(String username);

    boolean validateToken(String token, UserDetails userDetails);

    String extractUsername(String token);

    Boolean isTokenExpired(String token);
}
