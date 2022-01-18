package com.hust.backend.factory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralResponse<T> {

    private ResponseStatus status;

    @JsonProperty("data")
    private T data;

    public GeneralResponse(T data) {
        this.data = data;
    }
}