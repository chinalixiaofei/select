package lixf.select.main.com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date getAfterDate(Date date, int days) {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(6, calendar.get(6) + days);
		try {
			return df.parse(df.format(calendar.getTime()));
		} catch (ParseException e) {
			return null;
		}
	}


}
