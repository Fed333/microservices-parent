package com.epam.javacc.microservices.servo.metrics.utils;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Utility class with static methods for creating {@link Map} objects with prefilled data.
 * @author Roman Kovalchuk
 * */
public class MapUtils {

    /**
     * Creates prefilled parameterized {@link java.util.HashMap}. <br>
     * @param pairs two-dimensional array of object with data to be cast and inserted in the next format
     * <pre> {@code new Object[][]{
     *            {"key1", "value1"},
     *            {"key2", "value2"},
     *            {"key3", "value3"}
     * } }</pre>
     * @return Created {@link Map} object filled with given data
     * */
    @SuppressWarnings("unchecked")
    public static <K, V>  Map<K, V> createMap(Object[][] pairs) {
        return Stream.of(pairs)
                .collect(toMap(o -> (K)o[0], o -> (V)o[1]));
    }

    /**
     * Creates prefilled parameterized {@link java.util.HashMap}. <br>
     * @param keyClass generic type of the key
     * @param valueClass generic type of the value
     * @param pairs two-dimensional array of object with data to be cast and inserted in the next format
     * <pre> {@code new Object[][]{
     *            {"key1", "value1"},
     *            {"key2", "value2"},
     *            {"key3", "value3"}
     * } }</pre>
     * @return Created {@link Map} object filled with given data
     * */
    public static <K, V>  Map<K, V> createMap(Class<K> keyClass, Class<V> valueClass, Object[][] pairs) {
        return Stream.of(pairs)
                .collect(toMap(o -> keyClass.cast(o[0]), o -> valueClass.cast(o[1])));
    }

}
