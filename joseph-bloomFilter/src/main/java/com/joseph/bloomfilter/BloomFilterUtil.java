package com.joseph.bloomfilter;

/**
 * @author Joseph.Liu
 */
public class BloomFilterUtil {
    public static BitMapBloomFilter createBitMap(int m){
        return new BitMapBloomFilter(m);
    }
}
