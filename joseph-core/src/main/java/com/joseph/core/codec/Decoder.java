package com.joseph.core.codec;

/**
 * @author Joseph.Liu
 */
public interface Decoder <T,R>{
    R decode(T data);
}
