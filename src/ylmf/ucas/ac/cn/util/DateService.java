package ylmf.ucas.ac.cn.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateService {
	
	//private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat simpleDateFormat = null;
	
	public synchronized static SimpleDateFormat getSimpleDateFormat(String date_pattern) {
		if (simpleDateFormat == null)
				simpleDateFormat = new SimpleDateFormat(date_pattern);
		return simpleDateFormat;
	}
	private DateService() {
		// TODO Auto-generated constructor stub
	}

}
