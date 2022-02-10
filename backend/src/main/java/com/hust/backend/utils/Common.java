package com.hust.backend.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hust.backend.constant.ResponseStatusEnum;
import com.hust.backend.exception.Common.BusinessException;
import com.hust.backend.factory.GeneralResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
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

    public static Object getProperty(Object bean, String name) {
        try {
            return PropertyUtils.getProperty(bean, name);
        }
        catch (Exception ex) {
            log.error("get property failed!");
            return null;
        }
    }

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

    public static <T> T toObject(String text, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(text, valueTypeRef);
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

    public static BigInteger convertToBigInteger(String string){
        return BigInteger.valueOf(Long.parseLong(string));
    }

    private static boolean isValidGeneralResponse(GeneralResponse<?> response) {
        return response != null && response.getStatus() != null;
    }

    public static boolean isValidGeneralResponse(GeneralResponse<?> response, String statusCode) {
        return isValidGeneralResponse(response) &&
                StringUtils.equalsIgnoreCase(response.getStatus().getCode(), statusCode);
    }

    public static String toBearerToken(String accessToken) {
        return "Bearer " + accessToken;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target, String... ignoreProperties) {
        String[] toBeIgnored = ignoreProperties != null ?
                ArrayUtils.addAll(getNullPropertyNames(src), ignoreProperties) :
                getNullPropertyNames(src);
        BeanUtils.copyProperties(src, target, toBeIgnored);
    }

    public static <T, R> T convertObject(R source, Class<T> clazz) {
        try {
            T target = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            log.error("Convert object failed!");
        }
        return null;
    }

    public static Date convertLocalDateToDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static String formatDate(Date date, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String formatDate(Date date){
        return formatDate(date, "dd/MM/yyyy HH:mm");
    }
}
