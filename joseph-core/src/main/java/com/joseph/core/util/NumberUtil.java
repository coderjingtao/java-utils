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
     * default precision of division i.e. 0.01
     */
    private static final int DEFAULT_DIV_SCALE = 2;

    public static BigDecimal toBigDecimal(String numberStr){
        if(StrUtil.isBlank(numberStr)){
            return BigDecimal.ZERO;
        }
        return new BigDecimal(numberStr);
    }

    public static double add(double v1, double v2){
        return add(Double.toString(v1),Double.toString(v2)).doubleValue();
    }

    private static BigDecimal add(String... nums){
        if(ArrayUtil.isEmpty(nums)){
            return BigDecimal.ZERO;
        }
        String num = nums[0];
        BigDecimal result = new BigDecimal(num);
        for(int i = 1; i < nums.length; i++){
            result = result.add(new BigDecimal(nums[i]));
        }
        return result;
    }

    public static double sub(double v1, double v2){
        return sub(Double.toString(v1),Double.toString(v2)).doubleValue();
    }

    private static BigDecimal sub(String... nums){
        if(ArrayUtil.isEmpty(nums)){
            return BigDecimal.ZERO;
        }
        String num = nums[0];
        BigDecimal result = new BigDecimal(num);
        for(int i = 1; i < nums.length; i++){
            result = result.subtract(new BigDecimal(nums[i]));
        }
        return result;
    }

    public static double mul(double v1, double v2){
        return mul(Double.toString(v1), Double.toString(v2)).doubleValue();
    }

    private static BigDecimal mul(String... nums){
        if(ArrayUtil.isEmpty(nums) || ArrayUtil.hasNull(nums)){
            return BigDecimal.ZERO;
        }
        String num = nums[0];
        BigDecimal result = new BigDecimal(num);
        for(int i = 1; i < nums.length; i++){
            result = result.multiply(new BigDecimal(nums[i]));
        }
        return result;
    }

    public static double div(double v1, double v2){
        return div(Double.toString(v1),Double.toString(v2)).doubleValue();
    }
    public static double div(double v1, double v2, int scale){
        return div(Double.toString(v1),Double.toString(v2),scale).doubleValue();
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

    //---------------round------------------

    public static double round(double v){
        return round(v,DEFAULT_DIV_SCALE).doubleValue();
    }

    public static BigDecimal round(double v, int scale){
        return round(Double.toString(v),scale);
    }

    public static BigDecimal round(String numStr, int scale){
        if(scale < 0){
            scale = 0;
        }
        return round(toBigDecimal(numStr),scale,RoundingMode.HALF_UP);
    }

    public static BigDecimal round(BigDecimal num, int scale, RoundingMode roundingMode){
        if(num == null){
            return BigDecimal.ZERO;
        }
        if(scale < 0){
            scale = 0;
        }
        if(roundingMode == null){
            roundingMode = RoundingMode.HALF_UP;
        }
        return num.setScale(scale,roundingMode);
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

    public static String toStr(Number number){
        Assert.notNull(number,"Number is null");
        return number.toString();
    }
}
