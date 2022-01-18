package com.hust.backend.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Transformer {
    public static <E, M> List<M> listToList(List<E> source, Function<E, M> mapperFunction) {
        return ListUtils.emptyIfNull(source)
                        .stream()
                        .map(mapperFunction)
                        .collect(Collectors.toList());
    }

    public static <M> List<M> filter(List<M> source, Predicate<M> filter) {
        return ListUtils.emptyIfNull(source)
                        .stream()
                        .filter(filter)
                        .collect(Collectors.toList());
    }

    public static <M> List<M> limit(List<M> source, long maxSize) {
        return ListUtils.emptyIfNull(source)
                .stream()
                .limit(maxSize)
                .collect(Collectors.toList());
    }
}
