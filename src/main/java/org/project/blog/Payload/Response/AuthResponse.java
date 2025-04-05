package org.project.blog.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.blog.Constant.Enum.ROLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private long id;
    private String username;
    private String email;
    private ROLE role;
    private String avatar_url;
    private String accessToken;
    private String refreshToken;
}
