package com.joseph.core.util;

import com.joseph.core.lang.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author Joseph.Liu
 */
public class NumberUtil {

    /**
     * 默认除法运算精度
     */
    private static final int DEFAULT_DIV_SCALE = 10;

    public static BigDecimal toBigDecimal(String numberStr){
        if(StrUtil.isBlank(numberStr)){
            return BigDecimal.ZERO;
        }
        return new BigDecimal(numberStr);
    }

    public static BigDecimal div(String v1, String v2){
        return div(v1,v2,DEFAULT_DIV_SCALE);
    }

    public static BigDecimal div(String v1, String v2, int scale){
        return div(v1,v2,scale,RoundingMode.HALF_UP);
    }

    public static BigDecimal div(String v1, String v2, int scale, RoundingMode roundingMode){
        return div(toBigDecimal(v1),toBigDecimal(v2),scale,roundingMode);
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale, RoundingMode roundingMode){
        Assert.notNull(v2,"Divisor must be not null !");
        if(null == v1){
            return BigDecimal.ZERO;
        }
        if(scale < 0){
            scale = -scale;
        }
        return v1.divide(v2,scale,roundingMode);
    }
    //0.00 == 0
    public static boolean equals(double num1, double num2){
        return Double.doubleToLongBits(num1) == Double.doubleToLongBits(num2);
    }
    //0.00 == 0
    public static boolean equals(float num1, float num2){
        return Float.floatToIntBits(num1) == Float.floatToIntBits(num2);
    }

    public static boolean equals(final Number number1, final Number number2){
        if(number1 instanceof BigDecimal && number2 instanceof BigDecimal){
            //BigDecimal使用compareTo方式判断，因为使用equals方法也判断小数位数，如2.0和2.00就不相等
            return equal((BigDecimal) number1, (BigDecimal) number2);
        }
        return Objects.equals(number1,number2);
    }

    public static boolean equal(BigDecimal bigDecimal1, BigDecimal bigDecimal2){
        if(bigDecimal1 == bigDecimal2){
            return true;
        }
        if(bigDecimal1 == null || bigDecimal2 == null){
            return false;
        }
        return bigDecimal1.compareTo(bigDecimal2) == 0;
    }
}
