package com.blz.getresult.relevent.mapperandreducer;

import com.blz.getresult.relevent.writable.ComputerWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ComputerReducer extends Reducer<Text, ComputerWritable, Text, Text> {
	private Text valueOut = new Text();

	@Override
	protected void reduce(Text key, Iterable<ComputerWritable> values, Context context) throws IOException,
			InterruptedException {
		double fkl = 0;
		int lowmoney = 0, higmoney = 0, num = 0;
		for (ComputerWritable value : values) {
			fkl += value.getReleventNum();
			lowmoney += value.getLowMoney();
			higmoney += value.getHigMoney();
			++num;
		}

		int averMoney = (lowmoney + higmoney) / num;
		double averFkl = fkl / num;
		valueOut.set(averFkl + "\t" + averMoney);
		context.write(key, valueOut);
	}
}
