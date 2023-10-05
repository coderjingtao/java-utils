package com.joseph.core.util;

/**
 * @author Joseph.Liu
 */
public class HashUtil {

    public static int javaDefaultHash(String str){
        int h = 0;
        int off = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            h = 31 * h + str.charAt(off++);
        }
        return h;
    }

    /**
     * ELF算法
     *
     * @param str 字符串
     * @return hash值
     */
    public static int elfHash(String str) {
        int hash = 0;
        int x;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if ((x = (int) (hash & 0xF0000000L)) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }

        return hash & 0x7FFFFFFF;
    }

    /**
     * JS算法
     *
     * @param str 字符串
     * @return hash值
     */
    public static int jsHash(String str) {
        int hash = 1315423911;

        for (int i = 0; i < str.length(); i++) {
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        }

        return Math.abs(hash) & 0x7FFFFFFF;
    }
    /**
     * PJW算法
     *
     * @param str 字符串
     * @return hash值
     */
    public static int pjwHash(String str) {
        int bitsInUnsignedInt = 32;
        int threeQuarters = (bitsInUnsignedInt * 3) / 4;
        int oneEighth = bitsInUnsignedInt / 8;
        int highBits = 0xFFFFFFFF << (bitsInUnsignedInt - oneEighth);
        int hash = 0;
        int test;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash << oneEighth) + str.charAt(i);

            if ((test = hash & highBits) != 0) {
                hash = ((hash ^ (test >> threeQuarters)) & (~highBits));
            }
        }

        return hash & 0x7FFFFFFF;
    }
    /**
     * SDBM算法
     *
     * @param str 字符串
     * @return hash值
     */
    public static int sdbmHash(String str) {
        int hash = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }

        return hash & 0x7FFFFFFF;
    }
}
