package com.joseph.core.lang;

import com.joseph.core.util.StrUtil;

import java.util.function.Supplier;

/**
 * @author Joseph.Liu
 */
public class Assert {

    public static <X extends Throwable> void isTrue(boolean expression, Supplier<? extends X> supplier) throws X{
        if(!expression){
            throw supplier.get();
        }
    }

    public static void isTrue(boolean expression,String errorMsgTemplate, Object... params) throws IllegalArgumentException{
        isTrue(expression, () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate,params)));
    }

    public static <T> T notNull(T object) throws IllegalArgumentException{
        return notNull(object,"[Assertion failed] - this argument is required; it must not be null");
    }

    public static <T> T notNull(T object, String errorMsgTemplate, Object... params) throws IllegalArgumentException{
        return notNull(object, () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate,params)));
    }

    public static <T,X extends Throwable> T notNull(T object, Supplier<X> errorSupplier) throws X{
        if(null == object){
            throw errorSupplier.get();
        }
        return object;
    }
}
