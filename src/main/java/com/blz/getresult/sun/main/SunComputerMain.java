package com.blz.getresult.sun.main;

import com.blz.getresult.sun.inputformat.SunComputerInputFormat;
import com.blz.getresult.sun.mapperandreducer.SunComputerMapper;
import com.blz.getresult.sun.mapperandreducer.SunComputerReducer;
import com.blz.getresult.sun.writable.SunComputerWritable;
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


public class SunComputerMain extends Configured implements Tool {
	@Override
	public int run(String[] strings) throws Exception {
		Configuration configuration = new Configuration();
		configuration.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());

		String[] otherArgs = new GenericOptionsParser(configuration, strings).getRemainingArgs();
		Path outPath = new Path(otherArgs[otherArgs.length - 1]);

		FileSystem fileSystem = outPath.getFileSystem(configuration);
		if (fileSystem.exists(outPath)) {
			fileSystem.delete(outPath, true);
			System.out.println("存在的输出路径已删除");
		}

		Job job = new Job(configuration, "sunComputer");
		job.setJarByClass(SunComputerMain.class);

		for (int i = 0; i < otherArgs.length - 1; i++) {
			FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
		}
		FileOutputFormat.setOutputPath(job, outPath);

		job.setInputFormatClass(SunComputerInputFormat.class);
		job.setMapperClass(SunComputerMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(SunComputerWritable.class);

		job.setReducerClass(SunComputerReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public void SunComputerMainStart() throws Exception {
		String[] paths = {"hdfs://master:9000/user/hive/warehouse/computerdata/computerdata.txt",
				"hdfs://master:9000/out/Computer/suncomputer/"};
		ToolRunner.run(new SunComputerMain(), paths);
	}
}
