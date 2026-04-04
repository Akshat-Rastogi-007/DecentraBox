package com.codesmashers.decentrabox.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.codesmashers.decentrabox.security.user.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    @Value("${jwt.key}")
    private String jwtKey;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtKey.getBytes());
    }

    public String generateToken(UserDetailsImpl user, long expirationSecond) {

        Map<String, Object> claims = new HashMap<>();

        List<String> roles = user.getAuthorities()
                .stream()
                .map(
                        authority -> authority.getAuthority())
                .collect(Collectors.toList());

        claims.put("roles", roles);

        String jwtToken = Jwts.builder()
                .addClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationSecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return jwtToken;
    }

    public Claims getClaims(String jwtToken) {

        return Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJwt(jwtToken).getBody();

    }

    public String getSubject(String jwtToken) {

        return getClaims(jwtToken).getSubject();

    }

    public boolean isTokenExpired(String token) {
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
