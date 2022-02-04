package com.hust.backend.service.impl;

import com.hust.backend.config.ResetPasswordConfig;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.dto.request.ResetPasswordRequestDTO;
import com.hust.backend.dto.request.TokenRefreshRequestDTO;
import com.hust.backend.dto.request.UserLoginRequestDTO;
import com.hust.backend.dto.response.PayLoadResponseDTO;
import com.hust.backend.dto.response.RenewTokenResponseDTO;
import com.hust.backend.dto.response.UserLoginResponseDTO;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.exception.Common.BusinessException;
import com.hust.backend.exception.NotFoundException;
import com.hust.backend.exception.UnauthorizedException;
import com.hust.backend.model.token.TokenInfo;
import com.hust.backend.repository.UserRepository;
import com.hust.backend.service.JwtService;
import com.hust.backend.service.UserAuthenticationService;
import com.hust.backend.service.UserService;
import com.hust.backend.utils.ULID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService authService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final ResetPasswordConfig resetPasswordConfig;

    public UserAuthenticationServiceImpl(JwtService authService, UserService userService,
                                         UserRepository userRepository,
                                         BCryptPasswordEncoder bCryptPasswordEncoder,
                                         JavaMailSender mailSender,
                                         ResetPasswordConfig resetPasswordConfig) {
        this.authService = authService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailSender = mailSender;
        this.resetPasswordConfig = resetPasswordConfig;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
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
    @Transactional(rollbackOn = {Exception.class})
    public RenewTokenResponseDTO renewAccessToken(TokenRefreshRequestDTO request) {
        return authService.renewAccessToken(request.getAccessToken(), request.getRefreshToken());
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void sendPasswordResetToken(String email) {
        // create password reset token
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(UserEntity.class, email));
        String passwordToken = ULID.nextULID();
        setUserPasswordToken(user, passwordToken);

        // send email
        CompletableFuture.runAsync(() -> {
            sendPasswordResetToken(email, passwordToken);
            log.info("send email to " + email + "successfully");
        });

        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void resetPassword(ResetPasswordRequestDTO request) {
        // validate token
        UserEntity user = userRepository.findByPasswordToken(request.getToken())
                .orElseThrow(() -> {
                    log.error("Invalid Token");
                    throw new NotFoundException(UserEntity.class, request.getToken());
                });
        if (isTokenExpired(user.getTokenCreationDate())) {
            log.error("Token expired");
            resetUserPasswordToken(user);
            throw new BusinessException(ResponseStatusEnum.FORBIDDEN, "Expired token = " + request.getToken());
        }

        // reset user password
        updatePassword(user, request.getNewPassword());
    }

    private void sendPasswordResetToken(String email, String token) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(email);

            String subject = "Here's the link to reset your password";
            String url = resetPasswordConfig.getClientContextPath() + "/reset-password?token=" + token;
            helper.setSubject(subject);
            helper.setText(
                    "<html><body>" +
                            "<h1>" + "Reset password" + "</h1>" +
                            "<p>Hi there, </p>" +
                            "<p>" + "You have requested to reset your password." + "</p>" +
                            "<p>" + "Click the link below to change your password:</p>" +
                            "<p><a href=\"" + url + "\">Change my password</a></p>" +
                            "<br>" +
                            "<p>Ignore this email if you do remember your password or you have not made the request.</p>" +
                            "<br>" +
                            "<p style=\"color:#808080\"> @2021, GRBACKEND, All rights reserved.</p>" +
                            "<p style=\"color:#808080\">Our mailing address is: " +
                            "<a href=\"mailto:ictblackjack@gmail.com\" target=\"_blank\">ictblackjack@gmail.com</a>" +
                            "</p></body></html>"
                    , true
            );
            mailSender.send(message);
        } catch (MessagingException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void updatePassword(UserEntity user, String newPassword) {
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        resetUserPasswordToken(user);
    }

    private void resetUserPasswordToken(UserEntity user) {
        user.setPasswordToken(null);
        user.setTokenCreationDate(null);
        userRepository.save(user);
    }

    private void setUserPasswordToken(UserEntity user, String token) {
        user.setPasswordToken(token);
        user.setTokenCreationDate(new Date());
    }

    private boolean isTokenExpired(Date tokenCreationDate) {
        return new Date().getTime() - tokenCreationDate.getTime() > resetPasswordConfig.getTokenExpireTime().toMillis();
    }

}
