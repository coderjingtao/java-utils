package com.joseph.core.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * @author joseph
 * @create 2023-11-12
 */
public class CollUtil {

    // ----------------  new set -------------------
    public static <T> HashSet<T> newHashSet(Collection<T> collection){
        return newHashSet(false,collection);
    }
    public static <T> HashSet<T> newHashSet(boolean isSorted, Collection<T> collection){
        return isSorted ? new LinkedHashSet<>(collection) : new HashSet<>(collection);
    }
    public static <T> HashSet<T> newHashSet(T... data){
        return set(false,data);
    }
    public static <T> LinkedHashSet<T> newLinkedHashSet(T... data){
        return (LinkedHashSet<T>) set(true,data);
    }
    public static <T> HashSet<T> set(boolean isSorted, T... data){
        if(data == null){
            return isSorted ? new LinkedHashSet<>() : new HashSet<>();
        }
        int initCapacity = Math.max( (int)(data.length / 0.75f) + 1, 16);
        final HashSet<T> set = isSorted ? new LinkedHashSet<>(initCapacity) : new HashSet<>(initCapacity);
        Collections.addAll(set,data);
        return set;
    }

    // ----------------  new list -------------------


    // ----------------  isEmpty  -------------------
    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }
}
