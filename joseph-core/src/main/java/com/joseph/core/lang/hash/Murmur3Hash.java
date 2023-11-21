package com.joseph.core.lang.hash;

import com.joseph.core.util.ByteUtil;
import com.joseph.core.util.StrUtil;

import java.io.Serializable;
import java.nio.ByteOrder;

/**
 * Murmur3 32bit Hash Algorithm
 * @author Joseph.Liu
 */
public class Murmur3Hash implements Serializable {

    private static final long serialVersionUID = 1L;

    // Constants for 32 bit variant
    private static final int C1_32 = 0xcc9e2d51;
    private static final int C2_32 = 0x1b873593;
    private static final int R1_32 = 15;
    private static final int R2_32 = 13;
    private static final int M_32 = 5;
    private static final int N_32 = 0xe6546b64;

    private static final int DEFAULT_SEED = 0;
    private static final ByteOrder DEFAULT_ORDER = ByteOrder.LITTLE_ENDIAN;

    public static int hash32(CharSequence str){
        return hash32(StrUtil.bytes(str));
    }

    public static int hash32(byte[] data){
        return hash32(data,data.length,DEFAULT_SEED);
    }

    public static int hash32(byte[] data, int length, int seed){
        return hash32(data,0,length,seed);
    }

    public static int hash32(byte[] data, int offset, int length, int seed) {
        int hash = seed;
        final int nblocks = length >> 2;

        // body
        for (int i = 0; i < nblocks; i++) {
            final int i4 = offset  + (i << 2);
            final int k = ByteUtil.bytesToInt(data, i4, DEFAULT_ORDER);
            // mix functions
            hash = mix32(k, hash);
        }

        // tail
        final int idx = offset + (nblocks << 2);
        int k1 = 0;
        switch (offset + length - idx) {
            case 3:
                k1 ^= (data[idx + 2] & 0xff) << 16;
            case 2:
                k1 ^= (data[idx + 1] & 0xff) << 8;
            case 1:
                k1 ^= (data[idx] & 0xff);

                // mix functions
                k1 *= C1_32;
                k1 = Integer.rotateLeft(k1, R1_32);
                k1 *= C2_32;
                hash ^= k1;
        }

        // finalization
        hash ^= length;
        return fmix32(hash);
    }

    private static int mix32(int k, int hash) {
        k *= C1_32;
        k = Integer.rotateLeft(k, R1_32);
        k *= C2_32;
        hash ^= k;
        return Integer.rotateLeft(hash, R2_32) * M_32 + N_32;
    }

    private static int fmix32(int hash) {
        hash ^= (hash >>> 16);
        hash *= 0x85ebca6b;
        hash ^= (hash >>> 13);
        hash *= 0xc2b2ae35;
        hash ^= (hash >>> 16);
        return hash;
    }
}
