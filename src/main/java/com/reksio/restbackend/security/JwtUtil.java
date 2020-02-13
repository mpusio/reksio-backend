package com.reksio.restbackend.security;

import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;

import java.nio.charset.StandardCharsets;

import static com.reksio.restbackend.security.SecurityConstants.JWT_SECRET;

public class JwtUtil {
    public static String fetchEmail(String token){
        if (Strings.isNotEmpty(token)) {
            token = token.replace("Bearer ", "");
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
        throw new IllegalArgumentException("JWT Util: Cannot parse JWT");
    }
}
