package ylmf.ucas.ac.cn.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.conf.Configuration;

public class ThreadPoolExecutorService {

	private static ThreadPoolExecutorService instance = null;
	private static ThreadPoolExecutor threadPoolExecutor = null;

	public static synchronized ThreadPoolExecutor getInstance(
			Configuration configuration) {
		if (threadPoolExecutor == null)
			threadPoolExecutor = new ThreadPoolExecutor(configuration.getInt(
					"CORE_POOL_SIZE", 500), configuration.getInt(
					"MAXIMUM_POOL_SIZE", 1000), configuration.getLong(
					"KEEP_ALIVE_TIME", 1000),
					getTimeUnit(configuration.get("TIME_UNIT")),
					new ArrayBlockingQueue<Runnable>(configuration.getInt(
							"BLOCKING_QUEUE_CAPACITY", 100)),
					Executors.defaultThreadFactory(),
					new ThreadPoolExecutor.DiscardPolicy()
			);
		return threadPoolExecutor;

	}

	private static TimeUnit getTimeUnit(String timeUnitString) {
		// TODO Auto-generated method stub
		TimeUnit timeUnit = null;
		if (timeUnitString == null)
			timeUnit = TimeUnit.MILLISECONDS;
		else if (timeUnitString.equalsIgnoreCase("NANOSECONDS"))
			timeUnit = TimeUnit.NANOSECONDS;
		else if (timeUnitString.equalsIgnoreCase("MICROSECONDS"))
			timeUnit = TimeUnit.MICROSECONDS;
		else if (timeUnitString.equalsIgnoreCase("MILLISECONDS"))
			timeUnit = TimeUnit.MILLISECONDS;
		else if (timeUnitString.equalsIgnoreCase("SECONDS"))
			timeUnit = TimeUnit.SECONDS;
		else if (timeUnitString.equalsIgnoreCase("MINUTES"))
			timeUnit = TimeUnit.MINUTES;
		else if (timeUnitString.equalsIgnoreCase("HOURS"))
			timeUnit = TimeUnit.HOURS;
		else if (timeUnitString.equalsIgnoreCase("DAYS"))
			timeUnit = TimeUnit.DAYS;
		else
			timeUnit = TimeUnit.MICROSECONDS;
		return timeUnit;
	}

}
