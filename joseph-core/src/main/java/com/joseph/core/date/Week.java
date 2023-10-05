package com.joseph.core.date;

import com.joseph.core.lang.Assert;

import java.time.DayOfWeek;
import java.util.Calendar;

/**
 * the value equals the int value in the Calendar class
 * @author Joseph.Liu
 */
public enum Week {
    /**
     * value = 1
     */
    SUNDAY(Calendar.SUNDAY),
    /**
     * value = 2
     */
    MONDAY(Calendar.MONDAY),
    /**
     * value = 3
     */
    TUESDAY(Calendar.TUESDAY),
    /**
     * value = 4
     */
    WEDNESDAY(Calendar.WEDNESDAY),
    /**
     * value = 5
     */
    THURSDAY(Calendar.THURSDAY),
    /**
     * value = 6
     */
    FRIDAY(Calendar.FRIDAY),
    /**
     * value = 7
     */
    SATURDAY(Calendar.SATURDAY),
    ;
    private static final Week[] ENUMS = Week.values();
    private final int value;
    Week(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }

    /**
     * 1 (Monday) to 7 (Sunday).
     * @return ISO8601 int value
     */
    public int getIso8601Value(){
        int iso8601Value = this.value - 1;
        if(iso8601Value == 0){
            iso8601Value = 7;
        }
        return iso8601Value;
    }

    public static Week of(int calendarWeekValue){
        if(calendarWeekValue > ENUMS.length || calendarWeekValue < 1){
            return null;
        }
        return ENUMS[calendarWeekValue - 1];
    }

    public static Week of(DayOfWeek dayOfWeek){
        Assert.notNull(dayOfWeek);
        int calendarWeekValue = dayOfWeek.getValue() + 1;
        if(calendarWeekValue == 8){
            calendarWeekValue = 1;
        }
        return of(calendarWeekValue);
    }

    /**
     * Monday 1
     * Sunday 7
     * @return DayOfWeek of JDK
     */
    public DayOfWeek toJdkDayOfWeek(){
        return DayOfWeek.of(getIso8601Value());
    }
}
