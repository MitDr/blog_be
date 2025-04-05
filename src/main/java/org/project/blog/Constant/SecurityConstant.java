package org.project.blog.Constant;

public interface SecurityConstant {

    String[] ADMIN_API_PATHS = {

    };

    String[] CLIENT_API_PATHS = {

    };

    String[] IGNORE_API_PATHS = {
            "/api/v1/auth/**",
            "/api/v1/medias/**"

    };

    interface ROLE {
        String ADMIN = "ADMIN";
        String USER = "USER";
    }
}
