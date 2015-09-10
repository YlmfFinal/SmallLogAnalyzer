package ylmf.ucas.ac.cn.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPAddressService {
	
	private static Pattern pattern = null;
	private synchronized static Pattern getPattern(String ip_address_pattern) {
		if (pattern == null)
			pattern = Pattern.compile(ip_address_pattern);
		return pattern;
	}
	public static String getIPAddress(String ip_address_pattern) {
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(""+(random.nextInt(255)+1));
		for(int i = 0; i < 3; i++){
			stringBuilder.append("." + (random.nextInt(255)+1));
		}
		String ip_address = stringBuilder.toString(); 
		if(IPAddressService.getPattern(ip_address_pattern).matcher(ip_address).matches()){
			return ip_address;
		}else {
			return "127.0.0.1";
		}
	}	

}
