package net.transino.core.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lee
 * @ver 5.0
 */
public final class DateUtil {
    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
    public static final String DEFAULT_DATE = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_CHINESE = "yyyy年MM月dd日";
    public static final String DEFAULT_TIME = "HH:mm:ss";
    public static final String DEFAULT_TIME_WITHOUT_SECOND = "HH:mm";
    public static final String DEFAULT_DATE_AND_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_AND_TIME_CHINESE = "yyyy年MMt月dd日 HH:mm:ss";
    public static final String DEFAULT_DATE_AND_TIME_WITH_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DEFAULT_DATE_AND_TIME_WITHOUT_SECOND = "yyyy-MM-dd HH:mm";
    public static final String SIMPLE_DATE = "yyyyMMdd";

    private DateUtil() {
    }

    public static String toString(Date date) {
        return toString(date, "yyyy-MM-dd");
    }

    public static String toString(Date date, String pattern) {
        if(date == null) {
            return null;
        } else {
            pattern = (String)Util.defaultIfNull(pattern, "yyyy-MM-dd");
            return DateFormatUtils.format(date, pattern);
        }
    }

    public static Date toDateStrictly(String str) {
        return toDateStrictly(str, "yyyy-MM-dd");
    }

    public static Date toDateStrictly(String str, String pattern) {
        if(!Util.textHasContent(str)) {
            return null;
        } else {
            pattern = (String)Util.defaultIfNull(pattern, "yyyy-MM-dd");

            try {
                return DateUtils.parseDateStrictly(str, new String[]{pattern});
            } catch (ParseException var4) {
                String message = "parse dateString [{}] use patterns:[{}] to date exception,message:[{}]";
                throw new IllegalArgumentException(Util.format(message, new Object[]{str, pattern, var4.getMessage()}), var4);
            }
        }
    }



    public static String getDate() {
        return toString(new Date());
    }

    public static String getDateTime() {
        return toString(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDatetime(String pattern) {
        pattern = (String)Util.defaultIfNull(pattern, "yyyy-MM-dd HH:mm:ss");
        return toString(new Date(), pattern);
    }

    public static Date addDate(Date date, int field, int amount) {
        if(date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(field, amount);
            return calendar.getTime();
        }
    }

    public static Integer getDayBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(11, 0);
        start.set(12, 0);
        start.set(13, 0);
        start.set(14, 0);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.set(11, 0);
        end.set(12, 0);
        end.set(13, 0);
        end.set(14, 0);
        long n = end.getTimeInMillis() - start.getTimeInMillis();
        return Integer.valueOf((int)(n / 86400000L));
    }

    public static Integer getMonthBetween(Date startDate, Date endDate) {
        if(startDate != null && endDate != null && startDate.before(endDate)) {
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            int year1 = start.get(1);
            int year2 = end.get(1);
            int month1 = start.get(2);
            int month2 = end.get(2);
            int n = (year2 - year1) * 12;
            n = n + month2 - month1;
            return Integer.valueOf(n);
        } else {
            return null;
        }
    }

    public static Integer getMonthBetweenWithDay(Date startDate, Date endDate) {
        if(startDate != null && endDate != null && startDate.before(endDate)) {
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            int year1 = start.get(1);
            int year2 = end.get(1);
            int month1 = start.get(2);
            int month2 = end.get(2);
            int n = (year2 - year1) * 12;
            n = n + month2 - month1;
            int day1 = start.get(5);
            int day2 = end.get(5);
            if(day1 <= day2) {
                ++n;
            }

            return Integer.valueOf(n);
        } else {
            return null;
        }
    }
}