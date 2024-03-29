package com.hust.backend.controller.restful.advice;

import com.hust.backend.config.AppConfig;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.constant.ThreadContextKey;
import com.hust.backend.exception.Common.BaseException;
import com.hust.backend.exception.Common.BusinessException;
import com.hust.backend.exception.*;
import com.hust.backend.factory.GeneralResponse;
import com.hust.backend.factory.ResponseFactory;
import com.hust.backend.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
@Slf4j
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final ResponseFactory responseFactory;
    private final AppConfig appConfig;

    private String getTraceId() {
        return ThreadContext.get(ThreadContextKey.TRACE_ID);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponse<String>> handleExceptions(Exception ex) {
        log.info("handleExceptions", ex);
        return internalServerErrorResponse(ex.getMessage());
    }

    @ExceptionHandler({ IllegalArgumentException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<GeneralResponse<String>> handleIllegalArgumentException(Exception ex) {
        log.info("handleIllegalArgumentException", ex);
        return badRequestResponse("Illegal args or request.");
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<GeneralResponse<String>> handlePersistenceExceptions(PersistenceException ex) {
        log.info("handlePersistenceExceptions", ex);
        return badRequestResponse(ex.getMessage());
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<GeneralResponse<String>> handleDataNotValidExceptions(NotValidException ex) {
        log.info("handleDataNotValidExceptions", ex);
        return responseFactory.build(ResponseStatusEnum.DATA_NOT_VALID,
                ex.getData(),
                appConfig.getAppName(),
                appConfig.getEnv());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<GeneralResponse<String>> handleBindExceptions(BindException ex) {
        log.info("handleBindExceptions", ex);
        return badRequestResponse(ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toSet()));
    }

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<GeneralResponse<String>> handleEntityDuplicatedExceptions(DuplicatedException ex) {
        log.info("handleEntityDuplicatedExceptions", ex);
        if (ex.getTarget() != null) {
            return responseFactory.build(ResponseStatusEnum.ENTITY_DUPLICATED,
                    ex.getTarget().getName(),
                    ex.getIdentifier(),
                    appConfig.getAppName(),
                    appConfig.getEnv());
        } else {
            return responseFactory.build(ResponseStatusEnum.ENTITY_DUPLICATED,
                    ex.getMessage(),
                    StringUtils.EMPTY,
                    appConfig.getAppName(),
                    appConfig.getEnv());
        }
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GeneralResponse<String>> handleEntityNotFoundExceptions(NotFoundException ex) {
        log.info("handleEntityNotFoundExceptions", ex);
        if (ex.getTarget() != null) {
            return responseFactory.build(ResponseStatusEnum.ENTITY_NOT_FOUND,
                    ex.getTarget().getName(),
                    ex.getIdentifier(),
                    appConfig.getAppName(),
                    appConfig.getEnv());
        } else {
            return responseFactory.build(ResponseStatusEnum.ENTITY_NOT_FOUND,
                    ex.getMessage(),
                    StringUtils.EMPTY,
                    appConfig.getAppName(),
                    appConfig.getEnv());
        }
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GeneralResponse<String>> handleBusinessExceptions(BusinessException ex) {
        return responseFactory.build(ex.getResponseStatusEnum(), ex.getArgs());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<GeneralResponse<String>> handleBusinessExceptions(BaseException ex) {
        log.info(ex.getMessage(), ex);
        return internalServerErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse<String>> handleMethodArgumentNotValidExceptions(
            MethodArgumentNotValidException ex
    ) {
        log.info("handleMethodArgumentNotValidExceptions", ex);
        return badRequestResponse(ex.getBindingResult().getAllErrors().stream()
                                    .map(ObjectError::getDefaultMessage)
                                    .collect(Collectors.toSet()));
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<GeneralResponse<String>> handleServletRequestBindingExceptions(
            ServletRequestBindingException ex
    ) {
        log.info("handleServletRequestBindingExceptions", ex);
        return badRequestResponse(ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<GeneralResponse<String>> handleIOException(
            IOException ex
    ) {
        log.info("handleIOException", ex);
        return badRequestResponse(ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<GeneralResponse<String>> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        log.info("handleMaxSizeUploadException!");
        return responseFactory.build(ResponseStatusEnum.FILE_TOO_LARGE);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GeneralResponse<String>> handleConstraintViolationExceptions(
            ConstraintViolationException ex
    ) {
        log.info("handleConstraintViolationExceptions", ex);
        return badRequestResponse(ex.getConstraintViolations().stream()
                                    .map(ConstraintViolation::getMessage)
                                    .collect(Collectors.toSet()));
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<GeneralResponse<String>> handleRefreshTokenExceptions(RefreshTokenException ex) {
        log.info("handle refresh token exception", ex);
        return responseFactory.build(ResponseStatusEnum.FORBIDDEN); // pass msg arguments
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<GeneralResponse<String>> handleAuthExceptions(UnauthorizedException ex) {
        log.info("handle unauthorized exception", ex);
        return responseFactory.build(ResponseStatusEnum.UNAUTHORIZED, ex.getArgs());
        //        return responseFactory.build(ResponseStatusEnum.UNAUTHORIZED, ArrayUtils.addAll(ex.getArgs(), getTraceId())); // pass msg arguments
    }

    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<GeneralResponse<String>> methodNotSupportErrorHandler(HttpRequestMethodNotSupportedException e) {
        log.error("Http method not support exception: {}", e.getMessage());
        return responseFactory.build(ResponseStatusEnum.METHOD_NOT_SUPPORT, e.getMethod());
    }

    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
    public ResponseEntity<GeneralResponse<String>> mediaTypeNotSupportedErrorHandler(HttpMediaTypeNotSupportedException e) {
        log.error("Http media type not support exception: {}", e.getMessage());
        return responseFactory.build(ResponseStatusEnum.UNSUPPORTED_MEDIA_TYPE, Objects.requireNonNull(e.getContentType()).toString());
    }

    private <T> ResponseEntity<GeneralResponse<String>> badRequestResponse(T content) {
        return responseFactory.build(ResponseStatusEnum.BAD_REQUEST,
                Common.toJson(content),
                appConfig.getAppName(),
                appConfig.getEnv());
    }

    private <T> ResponseEntity<GeneralResponse<String>> internalServerErrorResponse(T content) {
        return responseFactory.build(ResponseStatusEnum.INTERNAL_SERVER_ERROR,
                content instanceof String ? content.toString() : Common.toJson(content),
                appConfig.getAppName(),
                appConfig.getEnv());
    }

}
