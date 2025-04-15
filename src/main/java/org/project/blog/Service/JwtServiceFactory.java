package org.project.blog.Service;

import lombok.RequiredArgsConstructor;
import org.project.blog.Constant.Enum.TOKENTYPE;
import org.project.blog.Service.Impl.AccessTokenService;
import org.project.blog.Service.Impl.RefreshTokenService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtServiceFactory {
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    public BaseJwtService getService(TOKENTYPE tokentype) {
        return switch (tokentype) {
            case ACCESS_TOKEN -> accessTokenService;
            case REFRESH_TOKEN -> refreshTokenService;
        };
    }
}
