package com.joseph.core.date;

import java.util.Calendar;

/**
 * @author Joseph.Liu
 */
public enum DateField {
    /**
     * value = 1
     */
    YEAR(Calendar.YEAR),
    /**
     * value = 2
     */
    MONTH(Calendar.MONTH),
    /**
     * value = 3
     */
    WEEK_OF_YEAR(Calendar.WEEK_OF_YEAR),
    /**
     * value = 4
     */
    WEEK_OF_MONTH(Calendar.WEEK_OF_MONTH),
    /**
     * value = 5
     */
    DAY_OF_MONTH(Calendar.DAY_OF_MONTH),
    /**
     * value = 6
     */
    DAY_OF_YEAR(Calendar.DAY_OF_YEAR),
    /**
     * value = 7
     * Sunday = 1, Monday = 2
     */
    DAY_OF_WEEK(Calendar.DAY_OF_WEEK),
    /**
     * value = 9
     */
    AM_PM(Calendar.AM_PM),
    ;

    private final int value;

    DateField(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }

    public static DateField of(int calendarFieldIntValue){
        switch (calendarFieldIntValue){
            case Calendar.YEAR:
                return YEAR;
            case Calendar.MONTH:
                return MONTH;
            case Calendar.WEEK_OF_YEAR:
                return WEEK_OF_YEAR;
            case Calendar.WEEK_OF_MONTH:
                return WEEK_OF_MONTH;
            case Calendar.DAY_OF_MONTH:
                return DAY_OF_MONTH;
            case Calendar.DAY_OF_YEAR:
                return DAY_OF_YEAR;
            case Calendar.DAY_OF_WEEK:
                return DAY_OF_WEEK;
            default:
                return null;
        }
    }
}
