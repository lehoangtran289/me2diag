package com.hust.backend.controller.restful.advice;

import com.hust.backend.exception.common.BusinessException;
import com.hust.backend.exception.common.ServiceError;
import com.hust.backend.exception.common.SysException;
import com.hust.backend.locale.I18nMessageHelper;
import com.hust.backend.response.ErrorResponse;
import com.hust.backend.response.Response;
import com.hust.backend.response.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.Set;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final I18nMessageHelper messageHelper;

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Response> validateErrorHandler(ConstraintViolationException e) {
        String errMsg = messageHelper.getErrorMessage(ServiceError.INVALID_PARAM.getMessageKey());
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        StringBuilder propertyErrMsg = new StringBuilder();
        for (ConstraintViolation<?> next : set) {
            propertyErrMsg.append(next.getMessage()).append(", ");
//         System.out.println(((PathImpl)next.getPropertyPath())
//                  .getLeafNode().getName() + "  " +next.getMessage());
        }
        return ResponseUtils.badRequest(
                ErrorResponse.of(ServiceError.INVALID_PARAM.getMessageKey(),
                        errMsg + ": " + propertyErrMsg));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Response> notValidArgumentErrorHandler(MethodArgumentNotValidException e) {
        String errMsg = messageHelper.getErrorMessage(ServiceError.INVALID_PARAM.getMessageKey());
//        BindingResult validateResult = e.getBindingResult();
//        final StringBuilder sb = new StringBuilder();
//        validateResult.getAllErrors().forEach((ObjectError err) ->
//            sb.append(err.getDefaultMessage() + ", ")
//        );
        String sb = e.getLocalizedMessage();
        return ResponseUtils.badRequest(
                ErrorResponse.of(ServiceError.INVALID_PARAM.getMessageKey(),
                        errMsg + ": " + sb));
    }
    
    
    @ExceptionHandler({ BindException.class })
    public ResponseEntity<Response> invalidRequestParamErrorHandler(BindException e) {
        String errMsg = messageHelper.getErrorMessage(ServiceError.INVALID_PARAM.getMessageKey());
//      BindingResult validateResult = e.getBindingResult();
//      final StringBuilder sb = new StringBuilder();
//      validateResult.getAllErrors().forEach((ObjectError err) ->
//          sb.append(err.getArguments() + " " + err.getDefaultMessage() + ", ")
//      );
        String sb = e.getLocalizedMessage();
        return ResponseUtils.badRequest(
                ErrorResponse.of(ServiceError.INVALID_PARAM.getMessageKey(), errMsg + ": " + sb));
    }
    
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Response> accessDeniedErrorHandler(AccessDeniedException e) {
        log.error("access denied", e);
        String errMsg = messageHelper.getErrorMessage(ServiceError.ACCESS_DENIED.getMessageKey());
        return ResponseUtils.unauthorized(ErrorResponse.of(ServiceError.ACCESS_DENIED.getMessageKey(), errMsg));
    }
    
    
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<Response> methodNotSupportErrorHandler(HttpRequestMethodNotSupportedException e) {
        log.error("Http method not support exception: {}", e.getMessage());
        String errMsg = messageHelper.getErrorMessage(ServiceError.HTTP_METHOD_NOT_SUPPORT.getMessageKey(), e.getMethod(), ArrayUtils.toString(e.getSupportedMethods()));
        return ResponseUtils.methodNotSupported(ErrorResponse.of(ServiceError.HTTP_METHOD_NOT_SUPPORT.getMessageKey(), errMsg));
    }
    
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
    public ResponseEntity<Response> mediaTypeNotSupportedErrorHandler(HttpMediaTypeNotSupportedException e) {
        log.error("Http media type not support exception: {}", e.getMessage());
        String errMsg = messageHelper.getErrorMessage(ServiceError.MEDIA_TYPE_NOT_SUPPORT.getMessageKey(), e.getContentType());
        return ResponseUtils.mediaTypeNotSupported(ErrorResponse.of(ServiceError.MEDIA_TYPE_NOT_SUPPORT.getMessageKey(), errMsg));
    }
    
    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<Response> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error("Http message not readable exception: {}", e.getMessage());
        String errMsg = messageHelper.getErrorMessage(ServiceError.MESSAGE_NOT_READABLE.getMessageKey(), e.getMostSpecificCause().getLocalizedMessage());
        return ResponseUtils.badRequest(ErrorResponse.of(ServiceError.MESSAGE_NOT_READABLE.getMessageKey(), errMsg));
    }
    
    @ExceptionHandler({ BusinessException.class })
    public ResponseEntity<Response> applicationErrorHandler(BusinessException e) {
        log.warn("Business Exception", e);
        String errMsg = messageHelper.getErrorMessage(e.getErr().getMessageKey(), e.getParams().values().toArray());
        return ResponseUtils.notSuccess(
                HttpStatus.valueOf(e.getErr().getErrCode()),
                ErrorResponse.of(e.getErr().getMessageKey(), errMsg, e.getParams()));
    }

    @ExceptionHandler({ Exception.class, SysException.class })
    public ResponseEntity<Response> unknownErrorHandler(Exception e) {
        log.error("Unexpected Exception", e);
        String errMsg = messageHelper.getErrorMessage(ServiceError.UNEXPECTED_EXCEPTION.getMessageKey());
        return ResponseUtils.internalErr(
                ErrorResponse.of(ServiceError.UNEXPECTED_EXCEPTION.getMessageKey(), errMsg));
    }
}
