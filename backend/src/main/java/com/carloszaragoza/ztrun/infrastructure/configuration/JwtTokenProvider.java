package com.carloszaragoza.ztrun.infrastructure.configuration;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long expiryMillis;

    public JwtTokenProvider(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expiryMillis
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiryMillis = expiryMillis;
    }

    public String createToken(String username, Set<String> roles) {
        long now = System.currentTimeMillis();
        Date exp = new Date(now + expiryMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.stream().collect(Collectors.toList()));

        return Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Set<String> getRoles(String token) {
        Object roles = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("roles");
        if (roles instanceof List<?> list) {
            return list.stream().map(Object::toString).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public long getExpiryMillis() {
        return expiryMillis;
    }
}
