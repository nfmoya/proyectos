package com.bbva.cfs.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	private static Calendar calendar = GregorianCalendar.getInstance();

	public static int getCurrentYear() {
		return calendar.get(calendar.YEAR);
	}

	public static int getCurrentMonth() {
		return calendar.get(calendar.MONTH) + 1;
	}
	
	public static int getCurrentDay(){
		return calendar.get(calendar.DAY_OF_MONTH);
	}
	
	public static String getStringCurrentDate(){
		return getCurrentYear() +
			   (getCurrentMonth()>=10?""+getCurrentMonth():"0"+getCurrentMonth()) +
			   (getCurrentDay()>=10?""+getCurrentDay():"0"+getCurrentDay());
	}
	
	public static Date formatStringToDate(String mask, String date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(mask);			
			return formatter.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String formatDateToString(Date dateToFormat){
		Calendar cal=Calendar.getInstance();
		cal.setTime(dateToFormat);
		return cal.get(calendar.YEAR) +
			   ((cal.get(calendar.MONTH)+1)<10?"0"+(cal.get(calendar.MONTH)+1):""+(cal.get(calendar.MONTH)+1)) +
			   (cal.get(calendar.DAY_OF_MONTH)<10?"0"+cal.get(calendar.DAY_OF_MONTH):""+cal.get(calendar.DAY_OF_MONTH));
	}
	
	/**
	 * Informa si es mayor de 16 o no.
	 * @param birthDate
	 * @return
	 */
	public static boolean isSixteenOlder(Date birthDate){
		try {
			if ((Long.valueOf(getStringCurrentDate())).intValue() - (Long.valueOf(formatDateToString(birthDate))).intValue() >= 160000)
				return true;
			else 
				return false;
		} catch (Exception e) {
			return false;
		}
	}
	
}
