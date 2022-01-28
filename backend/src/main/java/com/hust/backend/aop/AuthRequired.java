package com.hust.backend.aop;

import com.hust.backend.constant.UserRoleEnum;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1st arg là token. Kiem tra app name và token có quyền trong danh sách các quyền được yêu cầu hay không
 * Nếu ko thỏa mãn, trả FORBIDDEN
 * Nếu thỏa mãn, tìm argument có type là AccessTokenPayload để gán payload của token, nếu ko bỏ qua
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
public @interface AuthRequired {
    UserRoleEnum[] roles() default {};
}
