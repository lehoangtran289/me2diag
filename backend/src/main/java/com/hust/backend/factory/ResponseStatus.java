package com.hust.backend.factory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String code;
    private String message;
    @Builder.Default
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date responseTime = new Date();

//    public void setCode(String appName, String code) {
//        if (code == ResponseStatusEnum.SUCCESS.getCode()) {
//            this.code = ResponseStatusEnum.SUCCESS.getCode() + "0";
//            return;
//        }
//        this.code = StringUtils.leftPad(code, 4, "0");
//    }
}
