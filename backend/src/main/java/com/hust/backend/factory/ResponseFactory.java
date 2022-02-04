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
        ResponseEntity<GeneralResponse<T>> response = this.build(data, status, values);
        Objects.requireNonNull(response.getBody()).getStatus()
                .setCode(ResponseStatusEnum.SUCCESS.getCode());
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

    public ResponseEntity<GeneralResponse<Object>> build(
            ResponseStatus customResponseStatus,
            HttpStatus httpStatus
    ) {
        return ResponseEntity.status(httpStatus).body(buildGeneralResponse(null, customResponseStatus));
    }

    public <T> ResponseEntity<GeneralResponse<T>> build(
            T data,
            ResponseStatus customResponseStatus,
            HttpStatus httpStatus
    ) {
        return ResponseEntity.status(httpStatus).body(buildGeneralResponse(data, customResponseStatus));
    }

    public <T> ResponseEntity<GeneralResponse<T>> build(ResponseStatusEnum responseStatusEnum, String... values) {
        return build(null, responseStatusEnum, values);
    }

    public <T> ResponseEntity<GeneralResponse<T>> build(T data, ResponseStatusEnum responseStatusEnum, String... values) {
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

    private ResponseStatus parseResponseStatus(String code, String... values) {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setCode(code);
        String message = StringUtils.firstNonBlank(format(translator.getLocaleMessage(code), values), code);
        responseStatus.setMessage(message);
        return responseStatus;
    }

    /**
     * MessageFormat will replace args {#} in message.
     */
    private String format(String template, String... args) {
        return StringEscapeUtils.unescapeJava(MessageFormat.format(template, (Object[]) args));
    }

}
