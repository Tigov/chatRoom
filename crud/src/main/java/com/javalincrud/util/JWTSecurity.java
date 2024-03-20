package com.javalincrud.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JWTSecurity {

    private static byte[] SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded(); //generate key
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY);

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(KEY)
                .compact();
    }

    public static String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public static boolean validateToken(String token) {
        String extractedUsername = extractUsername(token);
        return extractedUsername != null;
    }
}
