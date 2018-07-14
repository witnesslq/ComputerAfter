package com.blz.getresult.sun.inputformat;

import com.blz.getresult.relevent.writable.ComputerWritable;
import com.blz.getresult.sun.writable.SunComputerWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;

import java.io.IOException;

public class SunComputerInputFormat extends FileInputFormat<Text, SunComputerWritable> {
	@Override
	public RecordReader<Text, SunComputerWritable> createRecordReader(InputSplit inputSplit, TaskAttemptContext
			taskAttemptContext) throws IOException, InterruptedException {
		return new SunComputerRecordReader();
	}

	public static class SunComputerRecordReader extends RecordReader<Text, SunComputerWritable> {
		// 行读取器
		private LineReader in;
		// 行类型
		private Text line;
		// 自定义key类型
		private Text lineKey;
		// 自定义value类型
		private SunComputerWritable lineValue;

		@Override
		public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException,
				InterruptedException {
			FileSplit fileSplit = (FileSplit) inputSplit;
			Configuration configuration = taskAttemptContext.getConfiguration();
			Path filePath = fileSplit.getPath();
			FileSystem fileSystem = filePath.getFileSystem(configuration);
			FSDataInputStream fsDataInputStream = fileSystem.open(filePath);

			in = new LineReader(fsDataInputStream, configuration);
			line = new Text();
			lineKey = new Text();
			lineValue = new SunComputerWritable();
		}

		@Override
		public boolean nextKeyValue() throws IOException, InterruptedException {
			int lineSize = in.readLine(line);
			if (lineSize == 0) {
				return false;
			}

			String[] lineValues = line.toString().split("\t");
			if (lineValues.length != 6) {
				return false;
			}

			String location = null;
			int sal = 0;
			String jobName = null;

			location = lineValues[5];
			sal = (Integer.parseInt(lineValues[3]) + Integer.parseInt(lineValues[4])) / 2;
			jobName = lineValues[0];

			lineKey.set(location + "\t" + jobName);
			lineValue.set(sal);
			return true;
		}

		@Override
		public Text getCurrentKey() throws IOException, InterruptedException {
			return lineKey;
		}

		@Override
		public SunComputerWritable getCurrentValue() throws IOException, InterruptedException {
			return lineValue;
		}

		@Override
		public float getProgress() throws IOException, InterruptedException {
			return 0;
		}

		@Override
		public void close() throws IOException {

		}
	}
}
