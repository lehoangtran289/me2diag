package com.hust.backend.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Common {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ignored) {
            return null;
        }
    }

    public static <T> T toObject(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String toJsonThrowable(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T toObjectThrowable(String text, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(text, clazz);
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Map<String, String> toFlattenedMap(Map<String, Object> map) {
        return map.entrySet().stream()
                .flatMap(Common::recursiveFlatten)
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
    }

    private static Stream<Map.Entry<String, ?>> recursiveFlatten(Map.Entry<String, ?> entry) {
        if (entry.getValue() instanceof Map) {
            return ((Map<String,?>) entry.getValue()).entrySet().stream().flatMap(Common::recursiveFlatten);
        }
        return Stream.of(entry);
    }
}
