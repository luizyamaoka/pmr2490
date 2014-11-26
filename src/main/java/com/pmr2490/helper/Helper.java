package com.pmr2490.helper;

import java.util.Calendar;
import java.util.Date;

public class Helper {

	public static String removeNonDigits(String str) {
	   if (str == null || str.length() == 0) {
	       return "";
	   }
	   return str.replaceAll("[^0-9]+", "");
	}
	
	public static Date buildDate(int year, int month, int day, Integer hour, Integer minute) {
		Calendar cal = Calendar.getInstance();
		if (hour == null || minute == null)
			cal.set(year, month-1, day);
		else 
			cal.set(year, month-1, day, hour, minute);
		return cal.getTime();
	}
	
}
