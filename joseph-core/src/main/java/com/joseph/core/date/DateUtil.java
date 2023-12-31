package com.joseph.core.date;

import com.joseph.core.comparator.CompareUtil;
import com.joseph.core.lang.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Joseph.Liu
 */
public class DateUtil extends CalendarUtil{

    public static DateTime toDateTime(Date date){
        Assert.notNull(date);
        return new DateTime(date);
    }

    public static int dayOfWeek(Date date){
        return DateTime.of(date).dayOfWeek();
    }
    public static Week daysOfWeek(Date date){
        return DateTime.of(date).dayOfWeekEnum();
    }
    public static boolean isWeekend(Date date){
        Week week = daysOfWeek(date);
        return Week.SATURDAY == week || Week.SUNDAY == week;
    }
    public static boolean isAM(Date date){
        return DateTime.of(date).isAM();
    }
    public static boolean isPM(Date date){
        return DateTime.of(date).isPM();
    }

    public static DateTime beginOfWeek(Date date){
        return new DateTime(beginOfWeek(toCalendar(date)));
    }
    public static DateTime endOfWeek(Date date){
        return new DateTime(endOfWeek(toCalendar(date)));
    }
    public static DateTime endOfBusinessWeek(Date date){
        return offsetDay(endOfWeek(date),-2);
    }
    public static DateTime lastWeek(Date date){
        return offsetWeek(date,-1);
    }
    public static DateTime nextWeek(Date date){
        return offsetWeek(date,1);
    }
    /**
     * next Monday
     * @param date specific one day
     * @return next Monday
     */
    public static DateTime beginOfNextWeek(Date date){
        return beginOfWeek(nextWeek(date));
    }
    /**
     * next Sunday
     * @param date specific one day
     * @return next Sunday
     */
    public static DateTime endOfNextWeek(Date date){
        return endOfWeek(nextWeek(date));
    }
    /**
     * next Friday
     * @param date specific one day
     * @return next Friday
     */
    public static DateTime endOfNextBusinessWeek(Date date){
        return offsetDay(endOfWeek(nextWeek(date)),-2);
    }
    public static DateTime thisTuesday(Date date){
        return offsetDay(beginOfWeek(date),1);
    }
    public static DateTime nextTuesday(Date date){
        return offsetDay(beginOfWeek(nextWeek(date)),1);
    }
    public static DateTime thisWednesday(Date date){
        return offsetDay(beginOfWeek(date),2);
    }
    public static DateTime nextWednesday(Date date){
        return offsetDay(beginOfWeek(nextWeek(date)),2);
    }

    public static DateTime beginOfMonth(Date date){
        return new DateTime(beginOfMonth(toCalendar(date)));
    }
    public static DateTime endOfMonth(Date date){
        return new DateTime(endOfMonth(toCalendar(date)));
    }
    public static DateTime lastMonth(Date date){
        return offsetMonth(date,-1);
    }
    public static DateTime nextMonth(Date date){
        return offsetMonth(date,1);
    }
    public static DateTime endOfNextMonth(Date date){
        return endOfMonth(nextMonth(date));
    }

    public static DateTime offset(Date date, DateField dateField, int offset){
        return toDateTime(date).offset(dateField,offset);
    }

    public static DateTime offsetDay(Date date, int offset){
        return offset(date,DateField.DAY_OF_YEAR, offset);
    }

    public static DateTime offsetWeek(Date date, int offset){
        return offset(date,DateField.WEEK_OF_YEAR, offset);
    }

    public static DateTime offsetMonth(Date date, int offset){
        return offset(date,DateField.MONTH,offset);
    }

    public static SimpleDateFormat newSimpleDateFormat(String pattern, Locale locale, TimeZone timeZone){
        if(locale == null){
            locale = Locale.getDefault(Locale.Category.FORMAT);
        }
        final SimpleDateFormat format = new SimpleDateFormat(pattern,locale);
        if(timeZone != null){
            format.setTimeZone(timeZone);
        }
        format.setLenient(false);
        return format;
    }
    public static SimpleDateFormat newSimpleDateFormat(String pattern){
        return newSimpleDateFormat(pattern, null, null);
    }

    public static DateTime parse(CharSequence dateStr, String format){
        return new DateTime(dateStr,format);
    }

    public static boolean isSameTime(Date date1, Date date2){
        return date1.compareTo(date2) == 0;
    }
    public static boolean isSameDay(Date date1, Date date2){
        return isSameDay(toCalendar(date1),toCalendar(date2));
    }

    public static boolean isIn(Date date, Date beginDate, Date endDate){
        if(date instanceof DateTime){
            return ((DateTime) date).isIn(beginDate,endDate);
        }else{
            return new DateTime(date).isIn(beginDate,endDate);
        }
    }

    public static int compare(Date date1, Date date2){
        return CompareUtil.compare(date1,date2);
    }
}
