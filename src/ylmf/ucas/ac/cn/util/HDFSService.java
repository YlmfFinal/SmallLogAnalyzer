package ylmf.ucas.ac.cn.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HDFSService {

	private static FSDataOutputStream fsDataOutputStream = null;
	private static FileSystem fileSystem = null;
	private static int count = 0;
	private static Path logFilePath = null;
	private static Random random = new Random();
	private static CountDownLatch countDownLatch = null;
	private static int entryCounts = 0;

	public static boolean generateLogFile(final Configuration configuration)
			throws IOException, InterruptedException {
		fileSystem = FileSystem.get(configuration);

		logFilePath = new Path(configuration.get("LOG_FILE"));
		fsDataOutputStream = fileSystem.create(logFilePath, true);
		countDownLatch = new CountDownLatch(configuration.getInt(
				"ENTRY_NUMBER", 100));

		for (int i = 0; i < configuration.getInt("ENTRY_NUMBER", 300); i++) {
			ThreadPoolExecutorService.getInstance(configuration).execute(
					new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep((random.nextInt(10)+1) * 10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							write(fsDataOutputStream, configuration);
							countDownLatch.countDown();
						}
					});
		}

		countDownLatch.await();
		fsDataOutputStream.close();
		if(ThreadPoolExecutorService.getInstance(configuration).getCompletedTaskCount() < configuration.getInt("ENTRY_NUMBER", 300))
			return false;
		else 
			return true;
		
		

	}

	public static synchronized void write(OutputStream outputStream, Configuration configuration) {
		count++;
		System.out
				.println("\u6b63\u5728\u5411\u65e5\u5fd7\u6587\u4ef6\u4e2d\u5199\u5165\u7b2c "
						+ count + " \u6761\u8bb0\u5f55 :");
		String entry = LogService.getInstance().getEntry(configuration);
		try {
			fsDataOutputStream.write(new StringBuilder(entry).append("\n").toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("[ " + entry + " ]");
	}

	public static void read(InputStream inputStream, OutputStream outputStream) {
		System.out.println("------------------------------------------------");
		try {
			IOUtils.copyBytes(inputStream, outputStream, 4096, false);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("------------------------------------------------");
	}

	public static int WriteLineInverse(DataInputStream dataInputStream,
			DataOutputStream dataOutputStream, Configuration configuration) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(dataOutputStream));
		
		String text = bufferedReader.readLine();
		while (entryCounts < configuration.getInt("TOP_NUMBER", 50) && text != null) {
			String[] values = text.split("\t");
			bufferedWriter.write(new StringBuilder().append(values[1]).append("\t").append(values[0]).append("\n").toString());
			text = bufferedReader.readLine();
			entryCounts++;
		}
		bufferedReader.close();
		bufferedWriter.close();
		dataInputStream.close();
		dataOutputStream.close();
		
		return entryCounts;

	}

	public static void main(String[] args) throws Exception {
		Configuration configuration = new Configuration();
		configuration.addResource("properties.conf.xml");
		System.out.println("------------------------------------------------");
		System.out.println("***\u5373\u5c06\u5411\u65e5\u5fd7\u6587\u4ef6\u4e2d\u5199\u5165 "
						+ configuration.getInt("ENTRY_NUMBER", 100)
						+ " \u6761\u8bb0\u5f55***");
		System.out.println("------------------------------------------------");

		if (generateLogFile(configuration)) {
			System.out
					.println("------------------------------------------------");
			System.out
					.println("---***\u6210\u529f\u751f\u6210\u65e5\u5fd7\u6587\u4ef6***---");
			System.out
					.println("------------------------------------------------");
		}

	}
}
