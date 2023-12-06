package com.joseph.core.lang;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Joseph.Liu
 */
public class Pair<K,V> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected K key;
    protected V value;

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    public static <K,V> Pair<K,V> of(K key, V value){
        return new Pair<>(key,value);
    }

    public K getKey(){
        return this.key;
    }

    public V getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return "Pair [key=" + key + ", value=" + value + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o instanceof Pair) {
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(getKey(), pair.getKey()) &&
                    Objects.equals(getValue(), pair.getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        //copy from 1.8 HashMap.Node
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }
}
