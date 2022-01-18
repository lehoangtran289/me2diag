package com.hust.backend.factory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hust.backend.constant.ResponseStatusEnum;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseStatus {
    @Setter(AccessLevel.NONE)
    private String code;
    private String message;
    @Builder.Default
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date responseTime = new Date();

    public void setCode(String appName, int code) {
        if (code == ResponseStatusEnum.SUCCESS.getCode()) {
            this.code = ResponseStatusEnum.SUCCESS.getCode() + "0";
            return;
        }
        this.code = StringUtils.leftPad(code + StringUtils.EMPTY, 4, "0");
    }
}
