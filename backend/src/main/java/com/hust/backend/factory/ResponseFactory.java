package com.hust.backend.factory;

import com.hust.backend.config.AppConfig;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.locale.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResponseFactory {
    private final AppConfig appConfig;
    private final Translator translator;

    public <T> ResponseEntity<GeneralResponse<T>> success() {
        return ResponseEntity.status(ResponseStatusEnum.SUCCESS.getHttpCode())
                             .body(buildGeneralResponse(null, ResponseStatusEnum.SUCCESS));
    }

    /**
     * @return response with message of ResponseStatusEnum request but using status SUCCESS
     */
    public <T> ResponseEntity<GeneralResponse<T>> success(T data, ResponseStatusEnum status, String... values) {
        ResponseEntity<GeneralResponse<T>> response = this.fail(data, status, values);
        Objects.requireNonNull(response.getBody()).getStatus()
               .setCode(appConfig.getAppName(), ResponseStatusEnum.SUCCESS.getCode());
        return response;
    }

    /**
     * @return response with message of ResponseStatusEnum request but using status SUCCESS
     */
    public <T> ResponseEntity<GeneralResponse<T>> success(ResponseStatusEnum status, String... values) {
        return this.success(null, status, values);
    }

    public <T> ResponseEntity<GeneralResponse<T>> success(T data, String... values) {
        return ResponseEntity.status(ResponseStatusEnum.SUCCESS.getHttpCode())
                             .body(buildGeneralResponse(data, ResponseStatusEnum.SUCCESS, values));
    }

    public ResponseEntity<GeneralResponse<Object>> fail(
            ResponseStatus customResponseStatus,
            HttpStatus httpStatus
    ) {
        return ResponseEntity.status(httpStatus).body(buildGeneralResponse(null, customResponseStatus));
    }

    public <T> ResponseEntity<GeneralResponse<T>> fail(
            T data,
            ResponseStatus customResponseStatus,
            HttpStatus httpStatus
    ) {
        return ResponseEntity.status(httpStatus).body(buildGeneralResponse(data, customResponseStatus));
    }

    public <T> ResponseEntity<GeneralResponse<T>> fail(
            ResponseStatusEnum responseStatusEnum, String... values
    ) {
        return fail(null, responseStatusEnum, values);
    }

    public <T> ResponseEntity<GeneralResponse<T>> fail(T data, ResponseStatusEnum responseStatusEnum, String... values) {
        return ResponseEntity.status(responseStatusEnum.getHttpCode())
                             .body(buildGeneralResponse(data, responseStatusEnum, values));
    }

    private <T> GeneralResponse<T> buildGeneralResponse(T data, ResponseStatusEnum status, String... values) {
        return buildGeneralResponse(data, parseResponseStatus(status.getCode(), values));
    }

    private <T> GeneralResponse<T> buildGeneralResponse(T data, ResponseStatus status) {
        GeneralResponse<T> generalResponse = new GeneralResponse<>();
        generalResponse.setData(data);
        generalResponse.setStatus(status);
        return generalResponse;
    }

    private ResponseStatus parseResponseStatus(
            int code,
            String... values
    ) {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setCode(appConfig.getAppName(), code);
        String message = StringUtils.firstNonBlank(format(translator.getLocaleMessage(code), values), responseStatus.getCode());
//        String displayMessage = StringUtils
//                .firstNonBlank(format(displayErrorService.getErrorDetail(code), values),
//                               message);
        responseStatus.setMessage(message);
        return responseStatus;
    }

    private String format(String template, String... args) {
        return StringEscapeUtils.unescapeJava(MessageFormat.format(template, (Object[]) args));
    }

}
