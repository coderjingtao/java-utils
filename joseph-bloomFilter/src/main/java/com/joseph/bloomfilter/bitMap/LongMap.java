package com.joseph.bloomfilter.bitMap;

import java.io.Serializable;

/**
 * @author Joseph.Liu
 */
public class LongMap implements BitMap, Serializable {
    private static final long serialVersionUID = 1L;

    private final long[] longs;

    /**
     * 构造
     */
    public LongMap() {
        longs = new long[93750000];
    }

    /**
     * 构造
     *
     * @param size 容量
     */
    public LongMap(int size) {
        longs = new long[size];
    }

    @Override
    public void add(long i) {
        int r = (int) (i / BitMap.MACHINE64);
        long c = i & (BitMap.MACHINE64 - 1);
        longs[r] = longs[r] | (1L << c);
    }

    @Override
    public boolean contains(long i) {
        int r = (int) (i / BitMap.MACHINE64);
        long c = i & (BitMap.MACHINE64 - 1);
        return ((longs[r] >>> c) & 1) == 1;
    }

    @Override
    public void remove(long i) {
        int r = (int) (i / BitMap.MACHINE64);
        long c = i & (BitMap.MACHINE64 - 1);
        longs[r] &= ~(1L << c);
    }
}
