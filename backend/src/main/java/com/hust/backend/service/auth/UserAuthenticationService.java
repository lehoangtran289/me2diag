package com.hust.backend.service.auth;

import com.hust.backend.dto.request.ResetPasswordRequestDTO;
import com.hust.backend.dto.request.UserLoginRequestDTO;
import com.hust.backend.dto.response.RenewTokenResponseDTO;
import com.hust.backend.dto.response.UserLoginResponseDTO;

public interface UserAuthenticationService {
    UserLoginResponseDTO buildLoginSuccessResponse(UserLoginRequestDTO request);

    RenewTokenResponseDTO renewAccessToken(String accessToken, String refreshToken);

    void sendPasswordResetToken(String email);

    void resetPassword(ResetPasswordRequestDTO request);
}
