import com.joseph.core.lang.reflect.ActualTypeMappingPool;
import com.joseph.core.util.ArrayUtil;
import com.joseph.core.util.ReflectUtil;
import com.joseph.core.util.TypeUtil;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Joseph.Liu
 */
public class TypeUtilTest {

    public static class TestClass{
        public List<String> getList(){
            return new ArrayList<>();
        }
    }

    public interface I<K>{

    }
    public static class A<T>{
    }
    public static class B extends A<String> implements I<Integer>{

    }

    @Test
    public void testAllGenerics(){
        ParameterizedType[] generics = TypeUtil.getGenerics(B.class);
        if(ArrayUtil.isNotEmpty(generics)){
            for(ParameterizedType parameterizedType : generics){
                System.out.println(parameterizedType);
                System.out.println(Arrays.toString(parameterizedType.getActualTypeArguments()));
            }
        }
    }

    @Test
    public void testGetElementType(){

        Method method = ReflectUtil.getMethod(TestClass.class, "getList");
        Type type = method.getGenericReturnType();
        System.out.println(type);
    }

    @Test
    public void testClassGetTypeArgument(){
        Type typeArgument = TypeUtil.getClass(B.class);
        if(typeArgument != null){
            System.out.println(typeArgument);
        }
    }

    @Test
    public void testCreateTypeMap(){

        Map<Type, Type> typeMap =
                ActualTypeMappingPool.createTypeMap(B.class);
        typeMap.forEach( (key,value) -> {
            System.out.println("key = " + key);
            System.out.println("value = " + value);
        });
    }

    @Test
    public void testTypeofType(){
        Field[] declaredFields = ParameterizedTypeTestBean.class.getDeclaredFields();
        for(Field field : declaredFields){
            Type genericType = field.getGenericType();
            System.out.print("[Field Type] = " + genericType.getTypeName() + ", ");
            System.out.print("[Field Name] = " + field.getName() + ", ");
            if(genericType instanceof ParameterizedType){
                System.out.println(" is a ParameterizedType");
            }
            if(genericType instanceof TypeVariable){
                System.out.println(" is a TypeVariable");
            }
        }
    }

    @Test
    public void testActualTypeArgumentsOfParameterizedType(){
        Field[] declaredFields = ParameterizedTypeTestBean.class.getDeclaredFields();
        for(Field field : declaredFields){
            Type genericType = field.getGenericType();
            if(genericType instanceof ParameterizedType){
                System.out.print("[Field Type] = " + genericType.getTypeName() + ", ");
                System.out.print("[Field Name] = " + field.getName() + ", ");

                Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
                for(Type type : actualTypeArguments){
                    System.out.print("[Arg Actual Type] = " + type.getTypeName() + " ");
                }
                System.out.println();
            }
        }
    }

    @Test
    public void testRawTypeOfParameterizedType(){
        Field[] declaredFields = ParameterizedTypeTestBean.class.getDeclaredFields();
        for(Field field : declaredFields){
            Type genericType = field.getGenericType();
            if(genericType instanceof ParameterizedType){
                System.out.print("[Field Type] = " + genericType.getTypeName() + ", ");
                System.out.print("[Field Name] = " + field.getName() + ", ");

                Type rawType = ((ParameterizedType) genericType).getRawType();
                System.out.print("[Raw Type] = " + rawType.getTypeName() + " ");
                System.out.println();
            }
        }
    }

    @Test
    public void testOwnerTypeOfParameterizedType(){
        Field[] declaredFields = ParameterizedTypeTestBean.class.getDeclaredFields();
        for(Field field : declaredFields){
            Type genericType = field.getGenericType();
            if(genericType instanceof ParameterizedType){
                System.out.print("[Field Type] = " + genericType.getTypeName() + ", ");
                System.out.print("[Field Name] = " + field.getName() + ", ");

                Type rawType = ((ParameterizedType) genericType).getOwnerType();
                System.out.print("[Raw Type] = " + (rawType == null ? "null" :  rawType.getTypeName()));
                System.out.println();
            }
        }
    }

    @Test
    public void testWildcardTypeOfParameterizedType(){
        Field[] declaredFields = ParameterizedTypeTestBean.class.getDeclaredFields();
        for(Field field : declaredFields){
            Type genericType = field.getGenericType();
            if(genericType instanceof ParameterizedType){
                System.out.print("[Field Type] = " + genericType.getTypeName() + ", ");
                System.out.print("[Field Name] = " + field.getName() + ", ");

                Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
                for(Type type : actualTypeArguments){
                    if(type instanceof WildcardType){
                        System.out.print("[Arg Actual Type] = [" + type.getTypeName() + "] is a wildcard, ");
                        Type[] lowerBounds = ((WildcardType) type).getLowerBounds();
                        for(Type type1 : lowerBounds){
                            System.out.println("LowerBounds: " + type1);
                        }
                        Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                        for(Type type1 : upperBounds){
                            System.out.println("UpperBounds: " + type1);
                        }
                    }
                }
                System.out.println();
            }
        }
    }

    @Test
    public void testTypeVariable(){
        Field[] declaredFields = TypeVariableTestBean.class.getDeclaredFields();
        for(Field field : declaredFields){
            Type genericType = field.getGenericType();
            System.out.print("[Field Type] = " + genericType.getTypeName() + ", ");
            System.out.print("[Field Name] = " + field.getName() + ", ");
            if(genericType instanceof TypeVariable){
                System.out.print("is [TypeVariable] ");
            }
            if(genericType instanceof ParameterizedType){
                System.out.print("is [ParameterizedType] ");
            }
            if(genericType instanceof GenericArrayType){
                System.out.print("is [GenericArrayType] ");
                Type genericComponentType = ((GenericArrayType) genericType).getGenericComponentType();
                if(genericComponentType instanceof TypeVariable){
                    System.out.print(" Component is [TypeVariable] ");
                }
            }
            if(genericType instanceof WildcardType){
                System.out.print("is [WildcardType] ");
            }
            if(genericType instanceof Class){
                System.out.print("is [Class] ");
            }
            System.out.println();
        }
    }

    @Test
    public void testTypeVariable2(){
        Field[] declaredFields = TypeVariableTestBean.class.getDeclaredFields();
        for(Field field : declaredFields){
            Type genericType = field.getGenericType();
            if(genericType instanceof TypeVariable){
                System.out.print("[Field Type] = " + genericType.getTypeName() + ", ");
                System.out.print("[Field Name] = " + field.getName() + ", ");
                System.out.print("is [TypeVariable], ");
                Type[] bounds = ((TypeVariable<?>) genericType).getBounds();
                for(Type type: bounds){
                    System.out.print("[Bounds] = "+type+ " ");
                }
                GenericDeclaration genericDeclaration = ((TypeVariable<?>) genericType).getGenericDeclaration();
                System.out.print("[GenericDeclaration] = " + genericDeclaration);
            }
            System.out.println();
        }
    }
}
