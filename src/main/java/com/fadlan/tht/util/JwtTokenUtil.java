package com.fadlan.tht.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtTokenUtil {

    private final String JWT_SECRET = "thisIsAReallyLongSecretKeyThatIsAtLeast64CharactersLongForHS512Algorithm";
    private final Key key;

    public JwtTokenUtil() {
        this.key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String getEmailFromToken(String token) {
        // token tanpa "Bearer "
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // versi baru
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // sub = email/username
    }

    public boolean validateToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
