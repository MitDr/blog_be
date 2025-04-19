package org.project.blog.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.blog.Constant.Enum.ROLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequest {
    private String username;
    private ROLE role;
    private String refreshToken;
    private String deviceId;
}
