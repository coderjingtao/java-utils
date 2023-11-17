package com.joseph.core.comparator;

/**
 * @author Joseph.Liu
 */
public class CompareUtil {

    public static <T extends Comparable<? super T>> int compare(T t1, T t2){
        return compare(t1,t2,false);
    }

    /**
     * safely compare object which implements Comparable
     * @param t1 obj1
     * @param t2 obj2
     * @param isNullGreatest true: null is greater than any other object
     * @param <T> type must implement java.lang.Comparable
     * @return t1 > t2 : 1, t1 < t2 : -1, t1 == t2 : 0
     */
    public static <T extends Comparable<? super T>> int compare(T t1, T t2, boolean isNullGreatest){
        if(t1 == t2){
            return 0;
        }else if(t1 == null){
            return isNullGreatest ? 1 : -1;
        }else if(t2 == null){
            return isNullGreatest ? -1 : 1;
        }
        return t1.compareTo(t2);
    }
}
