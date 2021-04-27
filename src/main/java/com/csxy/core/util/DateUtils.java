package com.csxy.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils{
	private static String CurrentTime;

	public static String getCurrentCompactTime() {
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		CurrentTime = formatter.format(NowDate);
		return CurrentTime;
	}

	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			result = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
