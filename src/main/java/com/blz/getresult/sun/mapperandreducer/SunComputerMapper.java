package com.blz.getresult.sun.mapperandreducer;

import com.blz.getresult.relevent.writable.ComputerWritable;
import com.blz.getresult.sun.writable.SunComputerWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SunComputerMapper extends Mapper<Text, SunComputerWritable, Text, SunComputerWritable> {
	@Override
	protected void map(Text key, SunComputerWritable value, Context context) throws IOException, InterruptedException {
		context.write(key, value);
	}
}
