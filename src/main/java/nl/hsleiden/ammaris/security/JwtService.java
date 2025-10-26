package nl.hsleiden.ammaris.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String base64Secret;

    @Value("${app.jwt.expiration}")
    private long expirationMs;

    private SecretKey key;

    @PostConstruct
    void init() {
        byte[] bytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(String subjectEmail) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(subjectEmail)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationMs))
                .signWith(key)
                .compact();
    }

    public String extractSubject(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
