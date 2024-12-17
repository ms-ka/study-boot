package studyboot.shop.security.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import studyboot.shop.model.entity.Role;
import studyboot.shop.repository.RoleRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import studyboot.shop.security.AuthInfo;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

//@Service
public class TokenService {

    private RoleRepository roleRepository;
    private SecretKey accessKey; //jeden klucz używany dla szyfrowania danych.
    private SecretKey refreshKey;

    //tworzenie sekretnych kluczy
    public TokenService(
            RoleRepository roleRepository,
            @Value("${key.access}") String accessSecretPhrase,
            @Value("${key.refresh}")String refreshSecretPhrase
    ) {
        this.roleRepository = roleRepository;
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretPhrase));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretPhrase));
    }

    //czas działania tokenu
    public String generateAccessToken(UserDetails user) {

        Instant now = Instant.now();
        Instant expiration = now.plus(7, ChronoUnit.DAYS);

        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(accessKey)
                .claim("roles", user.getAuthorities())
                .claim("name", user.getUsername())
                .compact();

    }
    public String generateRefreshToken(UserDetails user) {

        Instant now = Instant.now();
        Instant expiration = now.plus(20, ChronoUnit.DAYS);

        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessKey);

    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshKey);

    }

    public boolean validateToken (String token, SecretKey key) {

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    private Claims getClaimsFromToken (String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims getAccessClaimsFromToken(String accessToken) {
        return getClaimsFromToken(accessToken, accessKey);
    }
    public Claims getRefreshClaimsFromToken(String refreshToken) {
        return getClaimsFromToken(refreshToken, refreshKey);
    }

    public AuthInfo mapClaimsToAuthInfo (Claims claims) {
        String username = claims.getSubject();

        @SuppressWarnings("unchecked")
        List<Map<String, String>> roles = (List<Map<String, String>>) claims.get("roles", List.class);

        Set<Role> authorities = roles.stream()
                .map(m -> m.get("authority"))
                .map(s -> roleRepository.findRoleByTitle(s).orElseThrow(RuntimeException::new))
                .collect(Collectors.toSet());

        return new AuthInfo(username, authorities);
    }
}
