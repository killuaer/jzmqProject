package com.hhh.core.util;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Date getFirstDayOfYear(Integer year) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, year);
		date.set(Calendar.DAY_OF_YEAR, 1);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	}
	
	public static Date getLastestDayOfYear(Integer year) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, 11);
		date.set(Calendar.DAY_OF_MONTH, 31);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	}
	
	public static Date addMinute(Date date, Integer amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, amount);
		return c.getTime();
	}
	
	public static Date addDay(Date date, Integer amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, amount);
		return c.getTime();
	}
	
	public static Integer getDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	public static Date parseDate(String date) {
		if (date == null) return null;
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date parseDateTime(String date) {
		if (date == null) return null;
		try {
			return sdfFull.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String formatDate(Date date) {
		if (date == null) return null;
		return sdf.format(date);
	}
	
	public static String formatTimestamp(Timestamp date) {
		if (date == null) return null;
		return sdf.format(date);
	}
	
	public static String formatDateTime(Date date) {
		if (date == null) return null;
		return sdfFull.format(date);
	}
	
	public static Integer getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static Integer getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}

	public static Integer getDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static Boolean equals(Date date1, Date date2) {
		return date1.getTime() == date2.getTime();
	}

	public static Date getCurrentDateTime() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}
	
	public static boolean between(Date date, Date startDate, Date endDate) {
		return before(startDate, date) && after(date, endDate);
	}
	
	public static boolean after(Date date1, Date date2) {
		return date1.getTime() < date2.getTime();
	}
	
	public static boolean before(Date date1, Date date2) {
		return date1.getTime() < date2.getTime();
	}

	public static Date getCurrentDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	public static Date parseDate(Long value) {
		if (value == null) return null;
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(value);
		Date time = instance.getTime();
		return time;
	}
	
	 /**
     * 获得本月的开始时间
     * 
     * @return
     */
    public static Date getFirstDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, 1);
        return c.getTime();
    }

    /**
     * 当前月的结束时间，即2012-01-31 23:59:59
     * 
     * @return
     */
    public static Date getLastestDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }
	
	/**
	 * 当前季度的开始时间
	 * 
	 * @return
	 */
	public static Date getFirstDayOfQuarter() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		if (currentMonth >= 1 && currentMonth <= 3)
			c.set(Calendar.MONTH, 1);
		else if (currentMonth >= 4 && currentMonth <= 6)
			c.set(Calendar.MONTH, 3);
		else if (currentMonth >= 7 && currentMonth <= 9)
			c.set(Calendar.MONTH, 4);
		else if (currentMonth >= 10 && currentMonth <= 12)
			c.set(Calendar.MONTH, 9);
		c.set(Calendar.DATE, 1);
		return c.getTime();
	}

	/**
	* 当前季度的结束时间
	*
	* @return
	*/
	public static Date getLastestDayOfQuarter() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		if (currentMonth >= 1 && currentMonth <= 3) {
			c.set(Calendar.MONTH, 2);
			c.set(Calendar.DATE, 31);
		} else if (currentMonth >= 4 && currentMonth <= 6) {
			c.set(Calendar.MONTH, 5);
			c.set(Calendar.DATE, 30);
		} else if (currentMonth >= 7 && currentMonth <= 9) {
			c.set(Calendar.MONTH, 8);
			c.set(Calendar.DATE, 30);
		} else if (currentMonth >= 10 && currentMonth <= 12) {
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
		}
		return c.getTime();
	}
}
