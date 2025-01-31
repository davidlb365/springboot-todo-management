package net.david.todo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    // Generate JWT token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token =Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

//    private SecretKey key() {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // get username from JWT token
    public String getUsername(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(key())
//                .build()
//                .parseClaimsJwt(token)
//                .getBody();

        Claims claims = Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.getSubject();

        return username;
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parse(token);
        return true;
    }

}
