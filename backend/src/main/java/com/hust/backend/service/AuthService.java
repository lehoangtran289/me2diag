package com.hust.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.model.token.TokenInfo;
import io.jsonwebtoken.Claims;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AuthService {
    TokenInfo generateAccessToken(UserEntity userEntity);

    String generateRefreshToken(String accessTokenID);

    Long getUserIdFromToken(String token) throws InvalidKeySpecException;

    boolean validateToken(String token);

    Claims getClaims(String token) throws InvalidKeySpecException, NoSuchAlgorithmException;

    <T> T parse(String token, Class<T> clazz)
            throws InvalidKeySpecException, NoSuchAlgorithmException, JsonProcessingException;
}
