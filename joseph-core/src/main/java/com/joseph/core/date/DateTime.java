package com.joseph.core.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * java.util.Date with TimeZone
 * @author Joseph.Liu
 */
public class DateTime extends Date {
    private static final long serialVersionUID = -5395712593979185936L;
    private Week firstDayOfWeek = Week.MONDAY;
    private int minimalDaysInFirstWeek;
    private TimeZone timeZone;

    /**
     * format
     */
    /**
     * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 纯净日期时间格式，精确到日：yyyyMMdd
     */
    public static final String PURE_DATE_PATTERN = "yyyyMMdd";

    /**
     * Constructor start
     */
    public DateTime(){
        this(TimeZone.getDefault());
    }
    public DateTime(Date date){
        this(date, (date instanceof DateTime) ? ((DateTime)date).timeZone : TimeZone.getDefault());
    }
    public DateTime(Calendar calendar){
        this(calendar.getTime(),calendar.getTimeZone());
        this.setFirstDayOfWeek(Week.of(calendar.getFirstDayOfWeek()));
    }
    public DateTime(Date date, TimeZone timeZone){
        this(date.getTime(),timeZone);
    }
    public DateTime(TimeZone timeZone){
        this(System.currentTimeMillis(),timeZone);
    }
    public DateTime(long milliseconds, TimeZone timeZone){
        super(milliseconds);
        this.timeZone = timeZone;
    }
    public DateTime(CharSequence dateStr, String format){
        this(parse(dateStr,DateUtil.newSimpleDateFormat(format)));
    }
    /**
     * static of()
     */
    public static DateTime of(Date date){
        if(date instanceof DateTime){
            return (DateTime) date;
        }
        return new DateTime(date);
    }
    public static DateTime now(){
        return new DateTime();
    }

    /**
     * toCalendar()
     */
    public Calendar toCalendar(){
        return toCalendar(Locale.getDefault(Locale.Category.FORMAT));
    }

    public Calendar toCalendar(Locale locale){
        return toCalendar(this.timeZone,locale);
    }

    public Calendar toCalendar(TimeZone timeZone){
        return toCalendar(timeZone,Locale.getDefault(Locale.Category.FORMAT));
    }

    public Calendar toCalendar(TimeZone timeZone, Locale locale){
        if(locale == null){
            locale = Locale.getDefault(Locale.Category.FORMAT);
        }
        final Calendar cal = (timeZone != null) ? Calendar.getInstance(timeZone,locale) : Calendar.getInstance(locale);
        cal.setFirstDayOfWeek(firstDayOfWeek.getValue());
        if(minimalDaysInFirstWeek > 0){
            cal.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
        }
        cal.setTime(this);
        return cal;
    }

    /**
     * get() / set()
     */
    public Week getFirstDayOfWeek(){
        return this.firstDayOfWeek;
    }
    public DateTime setFirstDayOfWeek(Week firstDayOfWeek){
        this.firstDayOfWeek = firstDayOfWeek;
        return this;
    }
    public DateTime setTimeInternal(long time){
        super.setTime(time);
        return this;
    }
    /**
     * get a part of a date: YEAR, MONTH, WEEK, DAY, TIME, AM, PM
     */
    public int getField(DateField field){
        return getField(field.getValue());
    }
    public int getField(int field){
        return toCalendar().get(field);
    }

    public int dayOfWeek(){
        return getField(DateField.DAY_OF_WEEK);
    }
    public Week dayOfWeekEnum(){
        return Week.of(dayOfWeek());
    }
    public boolean isAM(){
        return Calendar.AM == getField(DateField.AM_PM);
    }
    public boolean isPM(){
        return Calendar.PM == getField(DateField.AM_PM);
    }

    /**
     * offset
     */
    public DateTime offset(DateField dateField, int offset){
        final Calendar cal = toCalendar();
        cal.add(dateField.getValue(),offset);
        return this.setTimeInternal(cal.getTimeInMillis());
    }

    @Override
    public String toString(){
        return toString(this.timeZone);
    }
    public String toString(TimeZone timeZone){
        return toString(DateUtil.newSimpleDateFormat(NORM_DATETIME_PATTERN,null,timeZone));
    }
    public String toString(DateFormat dateFormat){
        return dateFormat.format(this);
    }

    private static Date parse(CharSequence dateStr, DateFormat dateFormat){
        try {
            return dateFormat.parse(dateStr.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
