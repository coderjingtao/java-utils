package com.joseph.core.util;

import java.util.Objects;

/**
 * @author Joseph.Liu
 */
public class ObjUtil {

    public static boolean equals(Object obj1, Object obj2){
        if(obj1 instanceof Number && obj2 instanceof Number){
            return NumberUtil.equals((Number) obj1, (Number) obj2);
        }
        return Objects.equals(obj1,obj2);
    }

    public static boolean isNull(Object obj){
        return obj == null || obj.equals(null);
    }
    public static boolean isNotNull(Object obj){
        return obj != null && !obj.equals(null);
    }

    public static <T> T clone(T obj){
        T result = ArrayUtil.clone(obj);
        if(result == null){
            if(obj instanceof Cloneable){
                result = ReflectUtil.invoke(obj, "clone");
            }else{
                result = cloneByStream(obj);
            }
        }
        return result;
    }

    /**
     * 对象序列化后，通过拷贝流的方式进行克隆
     * 该对象必须实现序列化，否则返回null
     * @param obj 被克隆的对象
     * @param <T> 对象的类型
     * @return 克隆后的对象
     */
    public static <T> T cloneByStream(T obj){
        return SerializeUtil.clone(obj);
    }


}
