package com.joseph.core.lang.reflect;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 接口实现，用于重新定义泛型类型
 * @author Joseph.Liu
 */
public class ParamterizedTypeImpl implements ParameterizedType, Serializable {
    private static final long serialVersionUID = 1L;

    private final Type[] actualTypeArguments;
    private final Type ownerType;
    private final Type rawType;

    /**
     * 构造函数
     * @param actualTypeArguments 实际的泛型参数类型，e.g. List<String> 返回 String
     * @param ownerType 拥有者类型
     * @param rawType 原始类型，e.g. List<String> 返回 List
     */
    public ParamterizedTypeImpl(Type[] actualTypeArguments, Type ownerType, Type rawType){
        this.actualTypeArguments = actualTypeArguments;
        this.ownerType = ownerType;
        this.rawType = rawType;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    @Override
    public Type getRawType() {
        return rawType;
    }

    @Override
    public Type getOwnerType() {
        return ownerType;
    }
}
