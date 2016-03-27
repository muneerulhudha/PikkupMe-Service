package pikkup.util;

import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;

public class Util {
	
	static Gson gson = new Gson();
	
	public static String toJson(Object o) {
		return gson.toJson(o);
	}
	
	public static Class<?> fromJson(String json, Class<?> toClass) {
		return (Class<?>) gson.fromJson(json, toClass);
	}

	public static Date stringToDate(String string) {
		String[] date = string.split("/");
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.MONTH, new Integer(date[0])-1);
		calendar.set(Calendar.DATE, new Integer(date[1]));
		calendar.set(Calendar.YEAR, new Integer(date[2]));
		
		return calendar.getTime();
	}
	
}