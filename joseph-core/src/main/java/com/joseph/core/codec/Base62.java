package com.joseph.core.codec;


import com.joseph.core.util.StrUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Joseph.Liu
 */
public class Base62 {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static String encode(CharSequence source){
        return encode(source,DEFAULT_CHARSET);
    }

    public static String encode(CharSequence source,Charset charset){
        return encode(StrUtil.bytes(source,charset));
    }

    public static String encode(byte[] source){
        return new String(Base62Codec.INSTANCE.encode(source));
    }

    public static String decodeStr(CharSequence source){
        return decodeStr(source,DEFAULT_CHARSET);
    }

    public static String decodeStr(CharSequence source, Charset charset){
        return StrUtil.str(decode(source),charset);
    }

    public static byte[] decode(CharSequence cs){
        return decode(StrUtil.bytes(cs,DEFAULT_CHARSET));
    }

    public static byte[] decode(byte[] bytes){
        return Base62Codec.INSTANCE.decode(bytes);
    }
}
