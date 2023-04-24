package com.kunix.studentservice.model.hypersistence;

import java.io.Serializable;
import java.util.function.Function;

public final class Range<T extends Comparable<? super T>> implements Serializable {
    public static final String EMPTY = "empty";
    private final T lower;
    private final T upper;

    private Range(T lower, T upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public static <R extends Comparable<? super R>> Range<R> emptyRange() {
        return new Range<>(null, null);
    }

    public static <T extends Comparable<? super T>> Range<T> ofString(String str, Function<String, T> converter, Class<T> clazz) {
        if(str.equals(EMPTY)) {
            return emptyRange();
        }

        int delim = str.indexOf(',');

        if (delim == -1) {
            throw new IllegalArgumentException("Cannot find comma character");
        }

        String lowerStr = str.substring(1, delim);
        String upperStr = str.substring(delim + 1, str.length() - 1);

        T lower = converter.apply(lowerStr);
        T upper = converter.apply(upperStr);

        return new Range<>(lower, upper);
    }

    public static Range<Integer> integerRange(String range) {
        return ofString(range, Integer::parseInt, Integer.class);
    }

    @Override
    public String toString() {
        return "Range{" + "lower=" + lower +
                ", upper=" + upper +
                '}';
    }

    public boolean isLowerBoundClosed() {
        return true;
    }

    public boolean isUpperBoundClosed() {
        return true;
    }

}
