package com.hust.backend.service.impl;

import com.hust.backend.dto.request.TokenRefreshRequestDTO;
import com.hust.backend.dto.request.UserLoginRequestDTO;
import com.hust.backend.dto.response.PayLoadResponseDTO;
import com.hust.backend.dto.response.RenewTokenResponseDTO;
import com.hust.backend.dto.response.UserLoginResponseDTO;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.exception.UnauthorizedException;
import com.hust.backend.model.token.TokenInfo;
import com.hust.backend.repository.UserRepository;
import com.hust.backend.service.JwtService;
import com.hust.backend.service.UserAuthenticationService;
import com.hust.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final JwtService authService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserAuthenticationServiceImpl(JwtService authService, UserService userService,
                                         UserRepository userRepository,
                                         BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authService = authService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserLoginResponseDTO buildLoginSuccessResponse(UserLoginRequestDTO request) {
        // validate username/password
        Optional<UserEntity> optionalUser = EmailValidator.getInstance().isValid(request.getUsername()) ?
                userRepository.findByEmail(request.getUsername()) :
                userRepository.findByUsername(request.getUsername());
        UserEntity user = optionalUser.orElseThrow(() -> new UnauthorizedException("UserEntity.class", request.getUsername()));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Incorrect password: " + user.getPassword(), request.getUsername());
        }

        // build jwt token
        TokenInfo accessToken = authService.generateAccessToken(user);
        TokenInfo refreshToken = authService.generateRefreshToken(user, accessToken.getClaims().getId());

        return UserLoginResponseDTO.builder()
                .accessToken(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .payload(PayLoadResponseDTO.toDTO(accessToken.getClaims()))
                .build();
    }

    @Override
    public RenewTokenResponseDTO renewAccessToken(TokenRefreshRequestDTO request) {
        return authService.renewAccessToken(request.getAccessToken(), request.getRefreshToken());
    }

}
