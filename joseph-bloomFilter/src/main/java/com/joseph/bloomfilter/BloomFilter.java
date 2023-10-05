package com.joseph.bloomfilter;

import java.io.Serializable;

/**
 * @author Joseph.Liu
 */
public interface BloomFilter extends Serializable {

    boolean contains(String str);

    /**
     * 如果存在就返回{@code false} .如果不存在.先增加这个字符串.再返回{@code true}
     * @param str 字符串
     * @return 是否加入成功，如果存在就返回{@code false} .如果不存在返回{@code true}
     */
    boolean add(String str);
}
