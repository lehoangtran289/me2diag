package com.hust.backend.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hust.backend.dto.response.RenewTokenResponseDTO;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.model.token.TokenInfo;
import io.jsonwebtoken.Claims;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface JwtService {
    TokenInfo generateAccessToken(UserEntity userEntity);

    TokenInfo generateRefreshToken(UserEntity userEntity, String accessTokenID);

    RenewTokenResponseDTO renewAccessToken(String accessToken, String refreshToken);

    boolean isTokenValid(String token);

    Claims getClaims(String token) throws InvalidKeySpecException, NoSuchAlgorithmException;

    <T> T parse(String token, Class<T> clazz)
            throws InvalidKeySpecException, NoSuchAlgorithmException, JsonProcessingException;
}
