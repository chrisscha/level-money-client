package com.levelmoney.client.util;

/**
 * Useful interface for implementing things that you would like to Collect.
 * See java.util.stream.Collector for more information. This is especially useful for Collector.of(...)
 * and the method names match those conventions
 * <p>
 * Created by chris on 11/27/16.
 */
public interface Collectable<T> {

    T supply();

    void accumulate(T t);

    T combine(T t);
}
