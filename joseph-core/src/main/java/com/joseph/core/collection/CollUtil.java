package com.joseph.core.collection;

import com.joseph.core.map.MapUtil;

import java.util.*;

/**
 * @author joseph
 * @since 2023-11-12
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

    public static <T> ArrayList<T> newArrayList(T... data){
        return ListUtil.toList(data);
    }
    public static <T> ArrayList<T> toList(T... array){
        return ListUtil.toList(array);
    }
    public static <T> ArrayList<T> newArrayList(Collection<T> collection){
        return ListUtil.toList(collection);
    }

    public static <T> LinkedList<T> newLinkedList(T... data){
        return ListUtil.toLinkedList(data);
    }

    // ----------------  isEmpty / isNotEmpty -------------------


    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }
    public static boolean isEmpty(Map<?,?> map){
        return MapUtil.isEmpty(map);
    }

    public static boolean isNotEmpty(Collection<?> collection) { return !isEmpty(collection); }
    public static boolean isNotEmpty(Map<?,?> map){
        return MapUtil.isNotEmpty(map);
    }


    public static boolean contains(Collection<?> collection, Object value){
        return isNotEmpty(collection) && collection.contains(value);
    }




}
