package com.hust.backend.service;

import com.hust.backend.entity.UserEntity;

public interface JwtService {
    String generateAccessToken(UserEntity userEntity);

    String generateRefreshToken(String accessTokenID);

    Long getUserIdFromToken(String token);

    boolean validateToken(String token);
}
