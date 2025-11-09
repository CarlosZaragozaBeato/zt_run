package com.carloszaragoza.ztrun.infrastructure.configuration;

@Component
public class JwtTokenProvider {

    // TODO
    // mover a config segura
    private static final String SECRET_KEY =
        "SuperClaveUltraSeguraParaJWT1234567890"; // mover a config segura
    private static final long EXPIRATION_MS = 86400000;


    public String generateToken(String email, String role){
        return Jwts.builder()
            .setSubject(email)
            .claim("role", role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
            .signWith(keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
            .compat();
    }
    public String getEmailFromToken(String token){
        return Jwts.parserBuilder()
            .setSig
    }

}
