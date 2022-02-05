package com.hust.backend.service;

import com.hust.backend.dto.request.UserSignupRequestDTO;

public interface UserService {
    void registerUser(UserSignupRequestDTO request);
}
