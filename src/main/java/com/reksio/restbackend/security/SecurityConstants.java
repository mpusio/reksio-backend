package com.reksio.restbackend.security;

public class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/api/v1/login";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String JWT_SECRET = "t6w9z$C&F)J@McQfTjWnZr4u7x!A%D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeThVm";
    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
    public static final int TOKEN_EXPIRATION_TIME = 864000000;

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
