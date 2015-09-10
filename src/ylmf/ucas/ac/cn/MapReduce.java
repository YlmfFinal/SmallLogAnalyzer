package ylmf.ucas.ac.cn;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import ylmf.ucas.ac.cn.util.HDFSService;

public class MapReduce {
	private static int entryCounts = 0;
	public static class KeyWordsMapper extends
			Mapper<Object, Text, Text, IntWritable> {

		@Override
		protected void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			Text keyInfo = new Text();
			StringTokenizer stringTokenizer = new StringTokenizer(
					value.toString());
			stringTokenizer.nextToken();
			stringTokenizer.nextToken();
			keyInfo.set(stringTokenizer.nextToken());
			context.write(keyInfo, new IntWritable(1));
		}
	}

	public static class IntSumReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {                                                                                  

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			int sum = 0;
			for (IntWritable value : values) {
				sum += value.get();
			}
			key.set(key.toString());
			entryCounts++;
			context.write(key, new IntWritable(sum));
		}

	}

	public static class IntWritableDescComparator extends
			IntWritable.Comparator {

		@Override
		public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
			// TODO Auto-generated method stub
			return -super.compare(b1, s1, l1, b2, s2, l2);
		}

		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			// TODO Auto-generated method stub
			return -super.compare(a, b);
		}

	}

	public static class InverseMapper extends
			Mapper<Object, Text, IntWritable, Text> {

		@Override
		protected void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			StringTokenizer stringTokenizer = new StringTokenizer(value.toString());
			IntWritable keyInfo = new IntWritable();
			Text valueInfo = new Text();
			valueInfo.set(stringTokenizer.nextToken().getBytes());
			keyInfo.set(Integer.parseInt(stringTokenizer.nextToken()));
			context.write(keyInfo, valueInfo);
		}

	}
	
	private static boolean statisticFrequency(Configuration configuration) throws Exception, IOException{

		Job job = new Job(configuration, "Statistic Frequency");
		job.setJarByClass(MapReduce.class);
		job.setMapperClass(KeyWordsMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
    
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(job, new Path(configuration.get("INPUT_FILE")));
		FileOutputFormat.setOutputPath(job, new Path(configuration.get("TMP_FILE_DIR")));

		return job.waitForCompletion(true);
	}
	
	private static boolean descreaseSort(Configuration configuration) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException{
		Job job = new Job(configuration, "Descrese Sort");
		job.setJarByClass(MapReduce.class);
		job.setMapperClass(InverseMapper.class);

		job.setSortComparatorClass(IntWritableDescComparator.class);

		job.setNumReduceTasks(1);

		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(configuration.get("TMP_FILE_DIR")));
		FileOutputFormat.setOutputPath(job, new Path(configuration.get("OUTPUT_FILE_DIR")));

		return job.waitForCompletion(true);
	} 
	
	public static void main(String[] args) throws Exception {
		Configuration configuration = new Configuration();
		configuration.addResource("properties.conf.xml");
		
		System.out.println("------------------------------------------------");
		System.out.println("***\u5373\u5c06\u5411\u65e5\u5fd7\u6587\u4ef6\u4e2d\u5199\u5165 "
						+ configuration.getInt("ENTRY_NUMBER", 100)
						+ " \u6761\u8bb0\u5f55***");
		System.out.println("------------------------------------------------");

		if (HDFSService.generateLogFile(configuration)) {
			System.out
					.println("------------------------------------------------");
			System.out
					.println("---***\u6210\u529f\u751f\u6210\u65e5\u5fd7\u6587\u4ef6***---");
			System.out
					.println("------------------------------------------------");
			if (statisticFrequency(configuration)) {
				System.out.println("------------------------------------------------");
				System.out.println("---***\u6210\u529f\u7edf\u8ba1\u5404\u5173\u952e\u8bcd\u53ca\u5176\u51fa\u73b0\u6b21\u6570***---");
				System.out.println("------------------------------------------------");
				if (descreaseSort(configuration)) {
					System.out.println("------------------------------------------------");
					System.out.println("---***\u6210\u529f\u5b8c\u6210\u5404\u5173\u952e\u8bcd\u7684\u964d\u5e8f\u6392\u5217***---");
					System.out.println("------------------------------------------------");
					FileSystem fileSystem = FileSystem.get(configuration);
					
					HDFSService.WriteLineInverse(fileSystem.open(new Path(configuration.get("OUTPUT_FILE_DIR") + "/part-r-00000")), fileSystem.create(new Path(configuration.get("OUTPUT_FILE")),true), configuration);
					HDFSService.read(fileSystem.open(new Path(configuration.get("OUTPUT_FILE"))), System.out);
					
					System.out.println("------------------------------------------------");
					System.out.println("---***\u6210\u529f\u7edf\u8ba1\u5404\u5173\u952e\u8bcd\u53ca\u5176\u51fa\u73b0\u6b21\u6570***---");
					System.out.println("------------------------------------------------");
					
					System.exit(0);
				}
			}
		}

	}

}
