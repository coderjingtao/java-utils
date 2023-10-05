package com.joseph.core.codec;

/**
 * @author Joseph.Liu
 */
public interface Encoder<T,R> {
    R encode(T data);
}
