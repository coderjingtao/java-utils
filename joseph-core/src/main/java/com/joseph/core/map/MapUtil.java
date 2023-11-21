package com.joseph.core.map;

import java.util.Map;

/**
 * @author Joseph.Liu
 */
public class MapUtil {

    public static boolean isEmpty(Map<?,?> map){
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?,?> map){
        return map != null && !map.isEmpty();
    }


}
