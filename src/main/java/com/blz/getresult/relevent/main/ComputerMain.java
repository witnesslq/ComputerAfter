package com.blz.getresult.relevent.main;


import com.blz.getresult.relevent.inputformat.ComputerInputFormat;
import com.blz.getresult.relevent.mapperandreducer.ComputerMapper;
import com.blz.getresult.relevent.mapperandreducer.ComputerReducer;
import com.blz.getresult.relevent.writable.ComputerWritable;
import com.blz.getresult.utility.JDBC;
import com.blz.getresult.utility.ProList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.List;


public class ComputerMain extends Configured implements Tool {
	public static List<ProList> proListList = null;

	@Override
	public int run(String[] strings) throws Exception {
		JDBC jdbc = new JDBC();
		proListList = jdbc.getProList();

		Configuration configuration = new Configuration();
		configuration.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());

		String[] otherArgs = new GenericOptionsParser(configuration, strings).getRemainingArgs();
		Path outPath = new Path(otherArgs[otherArgs.length - 1]);

		FileSystem fileSystem = outPath.getFileSystem(configuration);
		if (fileSystem.exists(outPath)) {
			fileSystem.delete(outPath, true);
			System.out.println("存在的输出路径已删除");
		}

		Job job = new Job(configuration, "Computer");
		job.setJarByClass(ComputerMain.class);

		for (int i = 0; i < otherArgs.length - 1; i++) {
			FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
		}
		FileOutputFormat.setOutputPath(job, outPath);

		job.setInputFormatClass(ComputerInputFormat.class);
		job.setMapperClass(ComputerMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(ComputerWritable.class);

		job.setReducerClass(ComputerReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);


		return job.waitForCompletion(true) ? 0 : 1;
	}

	public void startComputerMain() throws Exception {
		String[] paths = {"hdfs://master:9000/user/hive/warehouse/computerdata/computerdata.txt",
				"hdfs://master:9000/out/Computer/computer/"};
		ToolRunner.run(new ComputerMain(), paths);
	}
}
