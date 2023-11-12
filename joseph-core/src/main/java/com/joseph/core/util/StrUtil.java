package com.joseph.core.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * String Tools
 * @author Joseph.Liu
 */
public class StrUtil extends CharSequenceUtil{
    public static final String EMPTY_JSON = "{}";
    public static final String UTF_8 = "UTF-8";
    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;

    private static char[] CHARS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private static int SIZE = CHARS.length;

    public static String utf8Str(Object obj){
        return str(obj,CHARSET_UTF_8);
    }
    public static String str(Object obj, Charset charSet){
        if(obj == null){
            return null;
        }
        if(obj instanceof String){
            return (String) obj;
        }else if(obj instanceof byte[]){
            return str( (byte[]) obj, charSet);
        }else if(obj instanceof Byte[]){
            return str( (Byte[]) obj, charSet);
        }else if(obj instanceof ByteBuffer){
            return str( (ByteBuffer) obj, charSet);
        }else if(ArrayUtil.isArray(obj)){
            return ArrayUtil.toString(obj);
        }
        return obj.toString();
    }
    public static String str(byte[] data, Charset charset){
        if(data == null){
            return null;
        }
        if(charset == null){
            return new String(data);
        }
        return new String(data,charset);
    }
    public static String str(Byte[] data, Charset charset){
        if(data == null){
            return null;
        }
        byte[] bytes = new byte[data.length];
        for(int i=0; i < data.length; i++){
            Byte dataByte = data[i];
            bytes[i] = dataByte == null ? -1 : dataByte;
        }
        return str(bytes,charset);
    }
    public static String str(ByteBuffer data, Charset charset){
        if(charset == null){
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }
    public static String decimalToBase62(long num){
        StringBuilder sb = new StringBuilder();
        while(num > 0){
             int remainder = (int) (num % SIZE);
             sb.append(CHARS[remainder]);
             num /= SIZE;
        }
        return sb.reverse().toString();
    }

    public static String hashToBase62(String str){
        int hash = Murmur3Hash.hash32(str);
        long num = hash < 0 ? Integer.MAX_VALUE - (long)hash : hash;
        return decimalToBase62(num);
    }


}
