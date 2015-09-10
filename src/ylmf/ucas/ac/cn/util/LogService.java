package ylmf.ucas.ac.cn.util;

import java.util.Date;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;

public class LogService {
	static int count = 1;

	private LogService() {
	}

	private static LogService instance = null;
//	private String FIELD_SEPARATOR = "\t\t";
//	private int KEYWORDS_LENGTH = 10;
//	private String IPADDRESS_PATTERN = "((1\\d{2}|([1-9]?\\d)|2[0-4]\\d|25[0-5])\\.){3}"
//			+ "(1\\d{2}|([1-9]?\\d)|2[0-4]\\d|25[0-5])";
//	private String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public synchronized static LogService getInstance() {
		if (instance == null) {
			instance = new LogService();
		}
		return instance;
	}

	public synchronized String getEntry(Configuration configuration) {
		StringBuilder entryBuilder = new StringBuilder();

		entryBuilder.append(getDate(configuration.get("DATE_PATTERN")));
		entryBuilder.append(configuration.getRaw("FIELD_SEPARATOR"));
		entryBuilder.append(getKeyWords(configuration.getInt("KEYWORDS_LENGTH",10)));
		entryBuilder.append(configuration.getRaw("FIELD_SEPARATOR"));
		entryBuilder.append(getIPAddress(configuration.get("IPADDRESS_PATTERN")));

		return entryBuilder.toString();
	}

	private String getDate(String date_pattern) {
		return DateService.getSimpleDateFormat(date_pattern).format(new Date());
	}

	private String getKeyWords(int keywords_length) {
		return KeyWordService.getKeyWords(keywords_length);
	}

	private String getIPAddress(String ip_address_pattern) {
		return IPAddressService.getIPAddress(ip_address_pattern);
	}

}
