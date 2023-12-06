package com.joseph.core.util;

import com.joseph.core.lang.reflect.ActualTypeMappingPool;
import com.joseph.core.lang.reflect.ParamterizedTypeImpl;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Joseph.Liu
 */
public class TypeUtil {
    //--------[Field Type 字段类型]----------------

    public static Type getType(Field field){
        return field == null ? null : field.getGenericType();
    }
    public static Class<?> getClass(Field field){
        return field == null ? null : field.getType();
    }
    public static Type getFieldType(Class<?> clazz, String fieldName){
        return getType(ReflectUtil.getField(clazz,fieldName));
    }

    //--------[Method Return Type]----------------

    /**
     * 获取方法的返回值类型
     * @param method 方法
     * @return 方法发返回值类型,可能为null
     */
    public static Type getReturnType(Method method){
        return method == null ? null : method.getGenericReturnType();
    }

    /**
     * 获取方法的返回值类型的类
     * @param method 方法
     * @return 返回值类型的类,可能为null
     */
    public static Class<?> getReturnClass(Method method){
        return method == null ? null : method.getReturnType();
    }

    //--------[Method Parameter Type]----------------

    /**
     * 获取方法的参数类型列表
     * @param method 方法
     * @return 方法的参数类型列表
     */
    public static Type[] getParameterTypes(Method method){
        return method == null ? null : method.getGenericParameterTypes();
    }

    public static Class<?>[] getParameterClasses(Method method){
        return method == null ? null : method.getParameterTypes();
    }

    public static Type getParameterType(Method method, int index){
        Type[] types = getParameterTypes(method);
        if(types != null && index < types.length){
            return types[index];
        }
        return null;
    }

    public static Class<?> getParameterClass(Method method, int index){
        Class<?>[] classes = getParameterClasses(method);
        if(classes != null && index < classes.length){
            return classes[index];
        }
        return null;
    }

    public static Type getFirstParameterType(Method method){
        return getParameterType(method,0);
    }

    public static Class<?> getFirstParameterClass(Method method){
        return getParameterClass(method,0);
    }

    //--------[Class Generic Type Argument 类的泛型参数]----------------

    public static Type[] getTypeArguments(Type type){
        if(type == null){
            return null;
        }
        ParameterizedType parameterizedType = toParameterizedType(type);
        return parameterizedType == null ? null : parameterizedType.getActualTypeArguments();
    }

    public static Type getTypeArgument(Type type, int index){
        Type[] typeArguments = getTypeArguments(type);
        if(typeArguments != null && index < typeArguments.length){
            return typeArguments[index];
        }
        return null;
    }

    public static Type getTypeArgument(Type type){
        return getTypeArgument(type,0);
    }

    //--------[Common]----------------

    /**
     * 将 Type 转换为 ParameterizedType (ParameterizedType用于获取当前类或父类中泛型参数化后的类型)
     * 该方法一般用于获取泛型参数具体的参数类型
     * class A<T>
     * class B extends A<String>;
     * 通过此方法，传入B.class, 可得到B对应的ParameterizedType,进而得到String
     * @param type Type
     * @param interfaceIndex 实现的第几个泛型父类或接口
     * @return 传入Type对应的ParameterizedType
     */
    public static ParameterizedType toParameterizedType(final Type type, final int interfaceIndex){
        if(type instanceof ParameterizedType){
            return (ParameterizedType) type;
        }

        if(type instanceof Class){
            final ParameterizedType[] generics = getGenerics((Class<?>) type);
            if(interfaceIndex < generics.length){
                return generics[interfaceIndex];
            }
        }
        return null;
    }
    public static ParameterizedType toParameterizedType(final Type type){
        return toParameterizedType(type, 0);
    }

    /**
     * 获取指定Class的所有泛型父类和泛型接口
     * interface I<K>{}
     * class A<T>{ }
     * 1.
     * class B extends A<String> implements I<Integer>{}
     * 通过此方法，传入B.class, 可得到
     * [$A<java.lang.String>,$I<java.lang.Integer>]
     * 2.
     * class B<T,K> extends A<T> implements I<K>{}
     * 通过此方法，传入B.class, 可得到
     * [$A<T>,$I<K>]
     * @param clazz 指定类
     * @return 泛型父类和泛型接口数组
     */
    public static ParameterizedType[] getGenerics(final Class<?> clazz){
        final List<ParameterizedType> result = new ArrayList<>();
        //泛型父类
        final Type genericSuper = clazz.getGenericSuperclass();
        if(genericSuper != null && !genericSuper.equals(Object.class)){
            ParameterizedType parameterizedType = toParameterizedType(genericSuper);
            if(parameterizedType != null){
                result.add(parameterizedType);
            }
        }
        //泛型接口
        final Type[] genericInterfaces = clazz.getGenericInterfaces();
        if(ArrayUtil.isNotEmpty(genericInterfaces)){
            for(Type genericInterface : genericInterfaces){
                if(genericInterface instanceof  ParameterizedType){
                    result.add((ParameterizedType) genericInterface);
                }
            }
        }
        return result.toArray(new ParameterizedType[0]);
    }

    /**
     * 获取Type对应的原始类
     * @param type
     * @return
     */
    public static Class<?> getClass(Type type){
        if(type != null){
            if(type instanceof Class){
                return (Class<?>) type;
            }else if(type instanceof ParameterizedType){
                return (Class<?>) ((ParameterizedType)type).getRawType();
            }else if(type instanceof TypeVariable){
                return (Class<?>)((TypeVariable<?>)type).getBounds()[0];
            }else if(type instanceof WildcardType){
                Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                if(upperBounds.length == 1){
                    return getClass(upperBounds[0]);
                }
            }
        }
        return null;
    }

    //--------[TypeVariable泛型变量]----------------


    public static boolean isUnknown(Type type){
        return type == null || type instanceof TypeVariable;
    }

    public static boolean hasTypeVariable(Type... types){
        for(Type type : types){
            if(type instanceof TypeVariable){
                return true;
            }
        }
        return false;
    }
    public static Map<Type,Type> getTypeMap(Class<?> clazz){
        return ActualTypeMappingPool.get(clazz);
    }

    public static Type getActualType(Type type, Field field){
        if(field == null){
            return null;
        }
        return getActualType(type == null ? field.getDeclaringClass() : type, field.getGenericType());
    }

    public static Type getActualType(Type type, Type typeVariable){
        if(typeVariable instanceof ParameterizedType){
            return getActualType(type,(ParameterizedType)typeVariable);
        }
        if(typeVariable instanceof TypeVariable){
            return ActualTypeMappingPool.getActualType(type,(TypeVariable<?>)typeVariable);
        }
        return typeVariable;
    }

    /**
     * 获得泛型变量T对应的泛型实际类型java.lang.String
     * 该方法可以处理负责的泛型对象，例如：Map<User,Key<Long>>
     * @param type 类,OrderMain
     * @param parameterizedType 泛型变量，List<T> items
     * @return T对应的实际类型
     */
    public static Type getActualType(Type type, ParameterizedType parameterizedType){

        // 字段类型为泛型参数类型，解析对应泛型类型为真实类型，类似于:Map<K,V> map
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        // 泛型对象中含有未被转换的泛型变量
        if(hasTypeVariable(actualTypeArguments)){
            actualTypeArguments = getActualTypes(type,parameterizedType.getActualTypeArguments());
            if(ArrayUtil.isNotEmpty(actualTypeArguments)){
                parameterizedType = new ParamterizedTypeImpl(actualTypeArguments,parameterizedType.getOwnerType(),parameterizedType.getRawType());
            }
        }
        return parameterizedType;
    }

    public static Type[] getActualTypes(Type type, Type... typeVariables){
        return ActualTypeMappingPool.getActualTypes(type,typeVariables);
    }
}
