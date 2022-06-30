package com.hust.backend.aop;

import com.hust.backend.config.AppConfig;
import com.hust.backend.config.AuthConfig;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.constant.TokenType;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.exception.Common.BusinessException;
import com.hust.backend.exception.UnauthorizedException;
import com.hust.backend.model.token.AccessTokenPayload;
import com.hust.backend.service.auth.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthAOP {

    private final JwtService authService;
    private final AppConfig appConfig;
    private final AuthConfig authConfig;

    @Pointcut("@annotation(authRequired)")
    public void pointcutAnnotationAuthentication(AuthRequired authRequired) {
        // do nothing
    }

    @Around(value = "pointcutAnnotationAuthentication(authRequired)", argNames = "joinPoint,authRequired")
    public Object aroundProcessAnnotation(ProceedingJoinPoint joinPoint, AuthRequired authRequired) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (!authConfig.isEnable()) {
            return setPayload(joinPoint, args, new AccessTokenPayload(appConfig.getAppName(),
                    appConfig.getAppName(),
                    appConfig.getAppName(),
                    new ArrayList<>()));
        }
        if (args.length <= 0 || !(args[0] instanceof String)) {
            log.info("AuthAOP verify: First argument must be access token");
            throw new UnauthorizedException("First arg must be token", "Missing access token");
        }

        AccessTokenPayload payload;
        String token = (String) args[0];

        // verify token
        try {
            payload = authService.parse(token, AccessTokenPayload.class);
        } catch (Exception e) {
            log.error("Token verification failed\n" + e.getMessage(), e);
            throw new BusinessException(ResponseStatusEnum.FORBIDDEN, "Token verification failed");
        }
        if (TokenType.ACCESS != TokenType.valueOf(payload.getType())) {
            log.error("Wrong token type");
            throw new BusinessException(ResponseStatusEnum.FORBIDDEN, "Wrong token type");
        }

        // verify roles
        List<UserRoleEnum> requiredPermissions = Arrays.asList(authRequired.roles());
        Set<UserRoleEnum> userPermissions = new HashSet<>(payload.getRoles());

        //TODO: remove later
        // permits all if role = ADMIN
        if (userPermissions.contains(UserRoleEnum.ADMIN)) {
            return setPayload(joinPoint, args, payload);
        }

        if (!userPermissions.containsAll(requiredPermissions)) {
            log.info("Do not have required permission(s)");
            throw new BusinessException(ResponseStatusEnum.FORBIDDEN);
        }
        return setPayload(joinPoint, args, payload);
    }

    private Object setPayload(ProceedingJoinPoint joinPoint, Object[] args, AccessTokenPayload payload) throws Throwable {
        Class<?>[] classes = ((CodeSignature) joinPoint.getSignature()).getParameterTypes();
        for (int i = 0; i < classes.length; i++) {
            if (classes[i] == AccessTokenPayload.class) {
                // got it, set payload then go
                args[i] = payload;
                return joinPoint.proceed(args);
            }
        }
        // not found payload type, keep going
        return joinPoint.proceed();
    }
}
