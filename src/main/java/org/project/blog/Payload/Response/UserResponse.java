package org.project.blog.Payload.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.blog.Constant.Enum.ROLE;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private ROLE role;
    private String avatar_url;
    private Date create_at;
}
