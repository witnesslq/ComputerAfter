package com.blz.getresult.sun.mapperandreducer;

import com.blz.getresult.sun.writable.SunComputerWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SunComputerReducer extends Reducer<Text, SunComputerWritable, Text, Text> {
	private Text valueOut = new Text();

	@Override
	protected void reduce(Text key, Iterable<SunComputerWritable> values, Context context) throws IOException,
			InterruptedException {
		int gradeSum =0;
		int num=0;
		for (SunComputerWritable value: values){
			gradeSum+=value.getMoney();
			++num;
		}
		valueOut.set(String.valueOf(gradeSum/num));
		context.write(key, valueOut);
	}
}
