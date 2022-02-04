package com.hust.backend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.backend.config.JwtConfig;
import com.hust.backend.constant.TokenType;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.response.RenewTokenResponseDTO;
import com.hust.backend.entity.RoleEntity;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.exception.*;
import com.hust.backend.model.token.TokenInfo;
import com.hust.backend.repository.UserRepository;
import com.hust.backend.service.JwtService;
import com.hust.backend.service.RoleService;
import com.hust.backend.utils.Transformer;
import com.hust.backend.utils.ULID;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {
    private static final String BEARER = "Bearer ";
    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper;

    private final RoleService roleService;
    private final UserRepository userRepository;

    public JwtServiceImpl(
            JwtConfig jwtConfig,
            @Qualifier("clientObjectMapper") ObjectMapper objectMapper,
            RoleService roleService,
            UserRepository userRepository) {
        this.jwtConfig = jwtConfig;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public TokenInfo generateAccessToken(UserEntity userEntity) {
        return generateAccessToken(ULID.nextULID(), userEntity);
    }

    @Override
    public TokenInfo generateRefreshToken(UserEntity userEntity, String accessTokenID) {
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + jwtConfig.getRefreshTokenExpTime().toMillis());

        Claims claims = Jwts.claims()
                .setId(ULID.nextULID())
                .setSubject(accessTokenID);
        claims.put("type", TokenType.REFRESH.toString());
        claims.put("username", userEntity.getUsername());
        claims.put("email", userEntity.getEmail());
        try {
            String refreshToken = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(iat)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getJwtSecret())
                    .compact();
            return TokenInfo.builder()
                    .token(refreshToken)
                    .claims(claims)
                    .build();
        } catch (Exception e) {
            throw new InternalException("refresh token generate exception");
        }
    }

    @Override
    public RenewTokenResponseDTO renewAccessToken(String accessToken, String refreshToken) {
        // validate accessToken, refreshToken
        Claims accessTokenClaims;
        try {
            accessTokenClaims = getClaims(accessToken);
        } catch (ExpiredJwtException e) {
            accessTokenClaims = e.getClaims();
        } catch (Exception e) { // generalize all jwt exceptions
            throw new RefreshTokenException("Verify access token failed", accessToken);
        }
        Claims refreshTokenClaims;
        try {
            refreshTokenClaims = getClaims(refreshToken);
        } catch (Exception e) { // generalize all jwt exceptions
            throw new RefreshTokenException("Verify refresh token failed", refreshToken);
        }
        if (!refreshTokenClaims.getSubject().equals(accessTokenClaims.getId())) {
            throw new RefreshTokenException("tokens not matched", accessToken, refreshToken);
        }

        String userId = accessTokenClaims.getSubject();
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(UserEntity.class, userId));

        // check token nearly exp?
        if (isTokenNearlyExpired(accessTokenClaims.getExpiration().getTime())) {
            // renew tokens
            TokenInfo newAccessToken = generateAccessToken(ULID.nextULID(), user);
            TokenInfo newRefreshToken = generateRefreshToken(user, newAccessToken.getClaims().getId());
            return RenewTokenResponseDTO.builder()
                    .accessToken(newAccessToken.getToken())
                    .refreshToken(newRefreshToken.getToken())
                    .build();
        }
        return RenewTokenResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
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
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtConfig.getJwtSecret())
                    .parseClaimsJws(token);
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

    private TokenInfo generateAccessToken(String accessTokenId, UserEntity userEntity) {
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + jwtConfig.getAccessTokenExpTime().toMillis());
        List<UserRoleEnum> userRoles = Transformer.listToList(
                roleService.getRolesFromUser(userEntity.getId()),
                RoleEntity::getRoleEnum);

        Claims claims = Jwts.claims()
                .setSubject(userEntity.getId())
                .setId(accessTokenId);
        claims.put("username", userEntity.getUsername());
        claims.put("email", userEntity.getEmail());
        claims.put("type", TokenType.ACCESS.toString());
        claims.put("roles", userRoles);

        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(iat)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getJwtSecret())
                    .compact();
            return TokenInfo.builder()
                    .token(token)
                    .claims(claims)
                    .build();
        } catch (Exception e) {
            throw new InternalException("refresh token generate exception");
        }
    }

    private String getUserIdFromAccessToken(String accessToken) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getJwtSecret())
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }

    private boolean isTokenNearlyExpired(long exp) {
        return exp - new Date().getTime() <= jwtConfig.getExpThreshold().toMillis();
    }

    private String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

}
