package com.hust.backend.service;

import com.hust.backend.dto.request.UserLoginRequestDTO;
import com.hust.backend.dto.response.UserLoginResponseDTO;

public interface UserAuthenticationService {
    UserLoginResponseDTO buildLoginSuccessResponse(UserLoginRequestDTO request);
}
