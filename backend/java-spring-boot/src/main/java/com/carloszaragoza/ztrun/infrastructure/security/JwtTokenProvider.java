package com.carloszaragoza.ztrun.infrastructure.security;


import com.carloszaragoza.ztrun.domain.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final String issuer;
    private final Key key;
    private final long expirationSeconds;

    public JwtTokenProvider(
            @Value("${security.jwt.issuer}") String issuer,
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration}") long expirationSeconds) {
        this.issuer = issuer;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationSeconds = expirationSeconds;
    }

    public String createToken(User user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationSeconds);

        var roles = user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet());
        var perms = user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream().map(p -> p.getName()))
                .collect(Collectors.toSet());

        return Jwts.builder()
                .issuer(issuer)
                .subject(user.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("roles", roles)
                .claim("perms", perms)
                .signWith(key)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith((SecretKey) key).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
}
