package com.hust.backend.service.impl;

import com.hust.backend.dto.request.UserLoginRequestDTO;
import com.hust.backend.dto.response.UserLoginResponseDTO;
import com.hust.backend.service.UserAuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Override
    public UserLoginResponseDTO buildLoginSuccessResponse(UserLoginRequestDTO request) {

        // validate username/password

        // build jwt token

        return null;
    }
}
