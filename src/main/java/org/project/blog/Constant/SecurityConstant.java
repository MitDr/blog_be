package org.project.blog.Constant;

public interface SecurityConstant {

    String[] ADMIN_API_PATHS = {
            "api/v1/admin/**",
    };

    String[] CLIENT_API_PATHS = {
            "api/v1/user/**",
    };

    String[] IGNORE_API_PATHS = {
            "/api/v1/public/**",
    };
    String[] AUTHENTICATION_API_PATHS = {
            "/api/v1/auth/**",
    };

    interface ROLE {
        String ADMIN = "ADMIN";
        String USER = "USER";
    }
}
