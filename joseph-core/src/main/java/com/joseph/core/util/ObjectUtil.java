package com.joseph.core.util;

import java.util.Objects;

/**
 * @author Joseph.Liu
 */
public class ObjectUtil {

    public static boolean equals(Object obj1, Object obj2){
        if(obj1 instanceof Number && obj2 instanceof Number){
            return NumberUtil.equals((Number) obj1, (Number) obj2);
        }
        return Objects.equals(obj1,obj2);
    }
}
