package com.joseph.core.collection;

import com.joseph.core.util.ArrayUtil;
import com.joseph.core.util.ObjUtil;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author joseph
 * @create 2023-11-12
 */
public class ListUtil {
    // ----------------  new list -------------------
    public static <T> List<T> list(boolean  isLinked){
        return isLinked ? new LinkedList<>() : new ArrayList<>();
    }
    public static <T> List<T> list(boolean isLinked, T... data){
        if(ArrayUtil.isEmpty(data)){
            return list(isLinked);
        }
        final List<T> list = isLinked ? new LinkedList<>() : new ArrayList<>(data.length);
        Collections.addAll(list,data);
        return list;
    }
    public static <T> List<T> list(boolean isLinked, Collection<T> collection){
        if(collection == null){
            return list(isLinked);
        }
        return isLinked ? new LinkedList<>(collection) : new ArrayList<>(collection);
    }
    public static <T> ArrayList<T> toList(T... data){
        return (ArrayList<T>) list(false,data);
    }
    public static <T> ArrayList<T> toList(Collection<T> collection){
        return (ArrayList<T>) list(false,collection);
    }
    public static <T> LinkedList<T> toLinkedList(T... data){
        return (LinkedList<T>) list(true,data);
    }
    public static <T> LinkedList<T> toLinkedList(Collection<T> collection){
        return (LinkedList<T>) list(true,collection);
    }
    public static <T> List<T> of(T... data){
        return ArrayUtil.isEmpty(data) ? Collections.emptyList() : Collections.unmodifiableList(toList(data));
    }
    public static <T>CopyOnWriteArrayList<T> toCopyOnWriteArrayList(Collection<T> collection){
        return collection == null ? new CopyOnWriteArrayList<>() : new CopyOnWriteArrayList<>(collection);
    }

    // ----------------  sort list -------------------

    //the original List will be modified
    public static <T> List<T> sort(List<T> list, Comparator<? super T> comparator){
        if(CollUtil.isEmpty(list)){
            return list;
        }
        list.sort(comparator);
        return list;
    }

    // ----------------  reverse list -------------------

    //the original List will be modified
    public static <T> List<T> reverse(List<T> list){
        Collections.reverse(list);
        return list;
    }

    public static <T> List<T> reverseNew(List<T> list){
        List<T> list2 = ObjUtil.clone(list);
        if(list2 == null){
            list2 = list(false,list);
        }
        try{
            return reverse(list2);
        }catch (UnsupportedOperationException e){
            //通过clone的list不可编辑，新建列表
            return reverse(list(false,list));
        }
    }
}
