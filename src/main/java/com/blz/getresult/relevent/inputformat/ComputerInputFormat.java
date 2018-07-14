package com.blz.getresult.relevent.inputformat;

import com.blz.getresult.relevent.writable.ComputerWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;

import java.io.IOException;

public class ComputerInputFormat extends FileInputFormat<Text, ComputerWritable> {
	@Override
	public RecordReader<Text, ComputerWritable> createRecordReader(InputSplit inputSplit, TaskAttemptContext
			taskAttemptContext) throws IOException, InterruptedException {

		return new ComputerRecordReader();
	}

	public static class ComputerRecordReader extends RecordReader<Text, ComputerWritable> {
		// 行读取器
		private LineReader in;
		// 行类型
		private Text line;
		// 自定义key类型
		private Text lineKey;
		// 自定义value类型
		private ComputerWritable lineValue;

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
			lineValue = new ComputerWritable();
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
			double fkl;
			int lowMoney, higMoney;
			location = lineValues[5];
			fkl = Double.parseDouble(lineValues[1]);
			lowMoney = Integer.parseInt(lineValues[3]);
			higMoney = Integer.parseInt(lineValues[4]);


			lineKey.set(location);
			lineValue.set(fkl, lowMoney, higMoney);
			return true;
		}

		@Override
		public Text getCurrentKey() throws IOException, InterruptedException {
			return lineKey;
		}

		@Override
		public ComputerWritable getCurrentValue() throws IOException, InterruptedException {
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
