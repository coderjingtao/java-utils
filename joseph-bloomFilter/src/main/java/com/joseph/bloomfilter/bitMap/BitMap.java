package com.joseph.bloomfilter.bitMap;

/**
 * @author Joseph.Liu
 */
public interface BitMap {

    int MACHINE32 = 32;
    int MACHINE64 = 64;

    /**
     * 加入值
     *
     * @param i 值
     */
    void add(long i);

    /**
     * 检查是否包含值
     *
     * @param i 值
     * @return 是否包含
     */
    boolean contains(long i);

    /**
     * 移除值
     *
     * @param i 值
     */
    void remove(long i);
}
