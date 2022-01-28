package com.hust.backend.service.impl;

import com.hust.backend.config.JwtConfig;
import com.hust.backend.constant.TokenType;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.exception.InternalException;
import com.hust.backend.service.JwtService;
import com.hust.backend.utils.ULID;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private final JwtConfig jwtConfig;

    public JwtServiceImpl(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public String generateAccessToken(UserEntity userEntity) {
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + jwtConfig.getAccessTokenExpTime().toMillis());
        try {
            return Jwts.builder()
                    .setSubject(userEntity.getId())
                    .setIssuedAt(iat)
                    .setExpiration(exp)
                    .signWith(
                            SignatureAlgorithm.HS512,
                            jwtConfig.getJwtSecret()
                    )
                    .compact();
        } catch (Exception e) {
            throw new InternalException("refresh token generate exception");
        }
    }

    @Override
    public String generateRefreshToken(String accessTokenID) {
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + jwtConfig.getRefreshTokenExpTime().toMillis());

        Claims claims = Jwts.claims()
                .setId(ULID.nextULID())
                .setSubject(accessTokenID);
        claims.put("type", TokenType.REFRESH.toString());

        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(iat)
                    .setExpiration(exp)
                    .signWith(
                            SignatureAlgorithm.RS256,
                            jwtConfig.getJwtSecret()
                    )
                    .compact();
        } catch (Exception e) {
            throw new InternalException("refresh token generate exception");
        }
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getJwtSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public boolean isTokenNearlyExpired(Duration exp) {
        return exp.toMillis() - new Date().getTime() <= jwtConfig.getExpThreshold().toMillis();
    }

}
