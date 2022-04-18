package com.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * Class to handle date and time stamp
 * 
 * @author 10675365
 * 
 */

public class CalendarUtils {

	public static String getTimeStamp(String dateFormat) {
		Date currentDate = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
		return dateFormatter.format(currentDate);
	}

	public static String getPreviousAndFutureDate(String futureDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
		calendar.add(Calendar.DATE, Integer.parseInt(futureDate));
		return dateFormatter.format(calendar.getTime());
	}

	public static String dateToString(String format, Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		String strDate = dateFormatter.format(date);

		return strDate;
	}

	public static Date stringToDate(String format, String strDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(strDate);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return date;
	}

	public static String getFuturnDate(String format, int numberOfDayAhead) {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, numberOfDayAhead);
		Date tomorrow = cal.getTime();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.format(tomorrow);
	}
}
