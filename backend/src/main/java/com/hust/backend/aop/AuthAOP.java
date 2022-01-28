package com.hust.backend.aop;

import com.hust.backend.config.AppConfig;
import com.hust.backend.config.AuthConfig;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.exception.Common.BusinessException;
import com.hust.backend.exception.InternalException;
import com.hust.backend.model.token.AccessTokenPayload;
import com.hust.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import com.hust.backend.constant.UserRoleEnum;

import java.util.*;

@SuppressWarnings("squid:S3776")
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthAOP {

    private final AuthService authService;
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

        if (args.length > 0 && args[0] instanceof String) {
            AccessTokenPayload payload;
            try {
                String token = (String) args[0];
                payload = authService.parse(token, AccessTokenPayload.class);
            } catch (Exception e) {
                throw new BusinessException(ResponseStatusEnum.FORBIDDEN);
            }
            List<UserRoleEnum> requiredPermissions = Arrays.asList(authRequired.roles());
            Set<UserRoleEnum> userPermissions = new HashSet<>();
            for (UserRoleEnum role : payload.getRoles()) {
                userPermissions.addAll(Collections.singleton(role));
            }
            if (userPermissions.containsAll(requiredPermissions)) {
                return setPayload(joinPoint, args, payload);
            }
            log.info("Check AuthAOP: Ko có permission yêu cầu");
            throw new BusinessException(ResponseStatusEnum.FORBIDDEN);
        }
        log.info("Check AuthAOP: Cần truyền token là 1st argument");
        throw new InternalException("First arg must be token");
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
