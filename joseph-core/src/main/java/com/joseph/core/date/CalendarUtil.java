package com.joseph.core.date;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Joseph.Liu
 */
public class CalendarUtil {



    public static Calendar toCalendar(Date date){
        return toCalendar(date.getTime());
    }

    public static Calendar toCalendar(long milliseconds){
        return toCalendar(milliseconds,TimeZone.getDefault());
    }

    public static Calendar toCalendar(long milliseconds, TimeZone timeZone){
        final Calendar cal = Calendar.getInstance(timeZone);
        cal.setTimeInMillis(milliseconds);
        return cal;
    }

    public static LocalDateTime toLocalDateTime(Calendar calendar){
        return LocalDateTime.ofInstant(calendar.toInstant(),calendar.getTimeZone().toZoneId());
    }

    /**
     * 获取指定日期字段的最小值，例如分钟的最小值是0
     * @param calendar calendar
     * @param dateField dateField
     * @return minimum value
     */
    public static int getBeginValue(Calendar calendar, int dateField){
        if(Calendar.DAY_OF_WEEK == dateField){
            return calendar.getFirstDayOfWeek();
        }
        return calendar.getActualMinimum(dateField);
    }
    public static int getBeginValue(Calendar calendar, DateField dateField){
        return getBeginValue(calendar,dateField.getValue());
    }

    /**
     * 获取指定日期字段的最大值，例如分钟的最大值是59
     * @param calendar calendar
     * @param dateField dateField
     * @return maximum value
     */
    public static int getEndValue(Calendar calendar, int dateField){
        if(Calendar.DAY_OF_WEEK == dateField){
            return (calendar.getFirstDayOfWeek() + 6) % 7;
        }
        return calendar.getActualMaximum(dateField);
    }
    public static int getEndValue(Calendar calendar, DateField dateField){
        return getEndValue(calendar,dateField.getValue());
    }

    /**
     * 修改日期为某个时间字段起始时间
     */
    public static Calendar truncate(Calendar calendar, DateField dateField){
        return DateModifier.modify(calendar,dateField.getValue(),DateModifier.ModifyType.TRUNCATE);
    }

    /**
     * 修改日期为某个时间字段四舍五入时间
     */
    public static Calendar round(Calendar calendar, DateField dateField){
        return DateModifier.modify(calendar,dateField.getValue(),DateModifier.ModifyType.ROUND);
    }

    /**
     * 修改日期为某个时间字段结束时间
     */
    public static Calendar ceiling(Calendar calendar, DateField dateField){
        return DateModifier.modify(calendar,dateField.getValue(),DateModifier.ModifyType.CEILING);
    }

    //给定日期的当前周的开始时间
    public static Calendar beginOfWeek(Calendar calendar, boolean isMondayFirstDay){
        calendar.setFirstDayOfWeek(isMondayFirstDay ? Calendar.MONDAY : Calendar.SUNDAY);
        // WEEK_OF_MONTH为上限的字段（不包括），实际调整的为DAY_OF_MONTH
        return truncate(calendar,DateField.WEEK_OF_MONTH);
    }

    public static Calendar beginOfWeek(Calendar calendar){
        return beginOfWeek(calendar,true);
    }

    public static Calendar endOfWeek(Calendar calendar, boolean isSundayLastDay){
        calendar.setFirstDayOfWeek(isSundayLastDay ? Calendar.MONDAY : Calendar.SUNDAY);
        return ceiling(calendar,DateField.WEEK_OF_MONTH);
    }

    public static Calendar endOfWeek(Calendar calendar){
        return endOfWeek(calendar,true);
    }

    public static Calendar beginOfMonth(Calendar calendar){
        return truncate(calendar,DateField.MONTH);
    }

    public static Calendar endOfMonth(Calendar calendar){
        return ceiling(calendar,DateField.MONTH);
    }

    public static boolean isSameDay(Calendar calendar1, Calendar calendar2){
        if(calendar1 == null || calendar2 == null){
            throw new IllegalArgumentException("date must not be null");
        }
        return calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA);
    }
}
