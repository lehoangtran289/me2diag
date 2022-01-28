package com.hust.backend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.backend.config.JwtConfig;
import com.hust.backend.constant.TokenType;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.entity.RoleEntity;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.exception.InternalException;
import com.hust.backend.model.token.TokenInfo;
import com.hust.backend.service.AuthService;
import com.hust.backend.service.RoleService;
import com.hust.backend.service.UserService;
import com.hust.backend.utils.Transformer;
import com.hust.backend.utils.ULID;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private static final String BEARER = "Bearer ";
    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper;

    private final UserService userService;
    private final RoleService roleService;

    public AuthServiceImpl(
            JwtConfig jwtConfig,
            @Qualifier("clientObjectMapper") ObjectMapper objectMapper,
            UserService userService,
            RoleService roleService) {
        this.jwtConfig = jwtConfig;
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public TokenInfo generateAccessToken(UserEntity userEntity) {
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + jwtConfig.getAccessTokenExpTime().toMillis());
        List<UserRoleEnum> userRoles = Transformer.listToList(
                roleService.getRolesFromUser(userEntity.getId()),
                RoleEntity::getRoleEnum);

        Claims claims = Jwts.claims()
                .setSubject(userEntity.getId())
                .setId(ULID.nextULID());
        String token;
        try {
            token = Jwts.builder()
                    .setSubject(userEntity.getId())
                    .setIssuedAt(iat)
                    .claim("roles", userRoles)
                    .setExpiration(exp)
                    .signWith(
                            SignatureAlgorithm.HS512,
                            jwtConfig.getJwtSecret()
                    )
                    .compact();
        } catch (Exception e) {
            throw new InternalException("refresh token generate exception");
        }
        return TokenInfo.builder()
                .token(token)
                .claims(claims)
                .build();
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
    public Long getUserIdFromToken(String token) throws InvalidKeySpecException {
        Claims claims = getClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    @Override
    public Claims getClaims(String token) throws InvalidKeySpecException {
        token = resolveToken(token);
        if (StringUtils.isBlank(token) || token.length() < 10) {
            throw new InvalidKeySpecException("Not a valid token");
        }
        return Jwts.parser()
                .setSigningKey(jwtConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            getClaims(token);
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
        } catch (Exception ex) {
            log.error("JWT claims string exception");
        }
        return false;
    }

    @Override
    public <T> T parse(String token, Class<T> clazz) throws InvalidKeySpecException, JsonProcessingException {
        Claims claims = getClaims(token);
        return objectMapper.readValue(objectMapper.writeValueAsString(claims), clazz);
    }

    public boolean isTokenNearlyExpired(Duration exp) {
        return exp.toMillis() - new Date().getTime() <= jwtConfig.getExpThreshold().toMillis();
    }

    private String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

}
