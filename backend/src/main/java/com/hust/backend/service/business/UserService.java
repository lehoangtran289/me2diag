package com.hust.backend.service.business;

import com.hust.backend.dto.request.UserInfoUpdateRequestDTO;
import com.hust.backend.dto.request.UserSignupRequestDTO;
import com.hust.backend.dto.response.UserInfoResponseDTO;

public interface UserService {
    void registerUser(UserSignupRequestDTO request);

    UserInfoResponseDTO updateUserInfo(String userId, UserInfoUpdateRequestDTO request);
}
