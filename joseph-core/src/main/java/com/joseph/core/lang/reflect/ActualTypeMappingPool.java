package com.joseph.core.lang.reflect;

import com.joseph.core.util.TypeUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 泛型变量和泛型实际类型映射关系缓存
 * @author Joseph.Liu
 */
public class ActualTypeMappingPool {

    private static final ConcurrentMap<Type, Map<Type,Type>> CACHE = new ConcurrentHashMap<>();

    public static Map<Type,Type> get(Type type){
        return CACHE.computeIfAbsent(type, ActualTypeMappingPool::createTypeMap);
    }

    /**
     * 获得泛型变量对应的泛型实际类型，如果此变量没有对应的实际类型，返回null
     * @param type
     * @param typeVariable
     * @return
     */
    public static Type getActualType(Type type, TypeVariable<?> typeVariable){
        final Map<Type, Type> typeMap = get(type);
        Type result = typeMap.get(typeVariable);
        while(result instanceof TypeVariable){
            result = typeMap.get(result);
        }
        return result;
    }

    public static Type[] getActualTypes(Type type, Type... typeVariables){
        final Type[] result = new Type[typeVariables.length];
        for(int i = 0; i < typeVariables.length; i++){
            result[i] = (typeVariables[i] instanceof TypeVariable) ? getActualType(type,(TypeVariable<?>)typeVariables[i]) : typeVariables[i];
        }
        return result;
    }

    /**
     * 创建类中所有的泛型变量和泛型实际类型的对应关系Map,
     * public class A<T>{}
     * public class B extends A<String>{}
     * 传入B.class后，输出 T -> class java.lang.String
     * @param type 类
     * @return map
     */
    public static Map<Type, Type> createTypeMap(Type type){
        final Map<Type, Type> typeMap = new HashMap<>();

        // 按继承层级寻找泛型变量和实际类型的对应关系
        // 在类中，对应关系分为两类：
        // 1. 父类定义变量，子类标注实际类型
        // 2. 父类定义变量，子类继承这个变量，让子类的子类去标注，以此类推
        // 此方法中我们将每一层级的对应关系全部加入到Map中，查找实际类型的时候，根据传入的泛型变量，
        // 找到对应关系，如果对应的是继承的泛型变量，则递归继续找，直到找到实际或返回null为止。
        // 如果传入的非Class，例如TypeReference，获取到泛型参数中实际的泛型对象类，继续按照类处理
        while (type != null){
            ParameterizedType parameterizedType = TypeUtil.toParameterizedType(type);
            if(parameterizedType == null){
                break;
            }
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();
            Type[] typeParameters = rawType.getTypeParameters();

            Type typeArgument;
            for(int i = 0; i < typeParameters.length; i++){
                typeArgument = typeArguments[i];
                //跳过泛型变量对应泛型变量的情况
                if(!(typeArgument instanceof TypeVariable)){
                    typeMap.put(typeParameters[i],typeArgument);
                }
            }

            type = rawType;
        }
        return typeMap;
    }
}
