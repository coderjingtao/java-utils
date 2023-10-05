package com.joseph.core.util;

import java.nio.ByteOrder;

/**
 * @author Joseph.Liu
 */
public class ByteUtil {

    /**
     * byte[] convert to int
     * @param bytes byte array
     * @param start start position
     * @param byteOrder byte order
     * @return integer value
     */
    public static int bytesToInt(byte[] bytes, int start, ByteOrder byteOrder){
        if(ByteOrder.LITTLE_ENDIAN == byteOrder){
            return bytes[start] & 0xFF | //
                    (bytes[1 + start] & 0xFF) << 8 | //
                    (bytes[2 + start] & 0xFF) << 16 | //
                    (bytes[3 + start] & 0xFF) << 24; //
        }else{
            return bytes[3 + start] & 0xFF | //
                    (bytes[2 + start] & 0xFF) << 8 | //
                    (bytes[1 + start] & 0xFF) << 16 | //
                    (bytes[start] & 0xFF) << 24; //
        }
    }
}
