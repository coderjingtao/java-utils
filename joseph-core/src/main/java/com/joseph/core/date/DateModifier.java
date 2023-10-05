package com.joseph.core.date;

import com.joseph.core.util.ArrayUtil;

import java.util.Calendar;

/**
 * @author Joseph.Liu
 */
public class DateModifier {

    /**
     * 忽略的计算的字段
     */
    private static final int[] IGNORE_FIELDS = new int[]{ //
            Calendar.HOUR_OF_DAY, // 与HOUR同名
            Calendar.AM_PM, // 此字段单独处理，不参与计算起始和结束
            Calendar.DAY_OF_WEEK_IN_MONTH, // 不参与计算
            Calendar.DAY_OF_YEAR, // DAY_OF_MONTH体现
            Calendar.WEEK_OF_MONTH, // 特殊处理
            Calendar.WEEK_OF_YEAR // WEEK_OF_MONTH体现
    };

    private static void modifyField(Calendar calendar, int field, ModifyType modifyType){
        // 12 hours update to 24 hours
        if(Calendar.HOUR == field){
            field = Calendar.HOUR_OF_DAY;
        }
        switch (modifyType){
            case TRUNCATE:
                calendar.set(field, DateUtil.getBeginValue(calendar,field));
                break;
            case CEILING:
                calendar.set(field,DateUtil.getEndValue(calendar,field));
                break;
            case ROUND:
                int min = DateUtil.getBeginValue(calendar,field);
                int max = DateUtil.getEndValue(calendar,field);
                int href;
                //星期特殊处理
                if(Calendar.DAY_OF_WEEK == field){
                    href = (min + 3) % 7;
                }else{
                    href = (max - min) / 2 + 1;
                }
                int value = calendar.get(field);
                calendar.set(field, (value < href) ? min : max);
                break;
        }
    }
    public static Calendar modify(Calendar calendar, int dateField, ModifyType modifyType){
        return modify(calendar,dateField,modifyType,false);
    }

    public static Calendar modify(Calendar calendar, int dateField, ModifyType modifyType, boolean truncateMilliseconds){
        final int endField = truncateMilliseconds ? Calendar.SECOND : Calendar.MILLISECOND;
        //循环处理各级字段
        for(int i = dateField + 1; i <= endField; i++){
            if(ArrayUtil.contains(IGNORE_FIELDS,i)){
                continue;
            }
            //在计算本周的起始和结束日时,忽略一些字段
            if (Calendar.WEEK_OF_MONTH == dateField || Calendar.WEEK_OF_YEAR == dateField) {
                if (Calendar.DAY_OF_MONTH == i) {
                    continue;
                }
            } else {
                if (Calendar.DAY_OF_WEEK == i) {
                    continue;
                }
            }
            modifyField(calendar,i,modifyType);
        }
        if(truncateMilliseconds){
            calendar.set(Calendar.MILLISECOND,0);
        }
        return calendar;
    }

    public static enum ModifyType{
        TRUNCATE, //表示这个字段不变，这个字段以下字段全部归0
        ROUND,
        CEILING;
    }
}
