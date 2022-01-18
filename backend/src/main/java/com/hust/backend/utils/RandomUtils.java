package com.hust.backend.utils;

import com.hust.backend.exception.InternalException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {
    private static final Map<Object, Integer> bucketMap = new ConcurrentHashMap<>(); // object, bucketSize

    public static <T> T random(List<T> objects, List<Integer> distributions) {
        if (objects.size() != distributions.size())
            throw new InternalException("Invalid input, object list size != distribution list size");

        List<T> toBeRandomized = new ArrayList<>();
        for (int i = 0; i < objects.size(); ++i) {
            T object = objects.get(i);
            Integer bucketSize = bucketMap.computeIfAbsent(object, o -> 0);
            if (bucketSize < distributions.get(i))
                toBeRandomized.add(object);
        }
        if (CollectionUtils.isEmpty(toBeRandomized)) {  // reset if all bucket is full
            for (T object : objects) {
                bucketMap.put(object, 0);
                toBeRandomized.add(object);
            }
        }
        // process
        int index = 0;
        double rdNumber = Math.random();
        double ratio = 1 / distributions.stream().mapToDouble(Integer::intValue).sum();
        int accumulatedDist = 0;
        for (int i = 0; i < toBeRandomized.size(); ++i) {
            accumulatedDist += distributions.get(i);
            if (rdNumber / ratio <= accumulatedDist) {
                index = i;
                break;
            }
        }
        T result = toBeRandomized.get(index);
        bucketMap.merge(result, 1, Integer::sum);
        return result;
    }
}
