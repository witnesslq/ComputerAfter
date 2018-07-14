package com.blz.getresult.relevent.mapperandreducer;

import com.blz.getresult.relevent.main.ComputerMain;
import com.blz.getresult.relevent.writable.ComputerWritable;
import com.blz.getresult.utility.ProList;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

public class ComputerMapper extends Mapper<Text, ComputerWritable, Text, ComputerWritable> {
	Text keyOut = new Text();
	List<ProList> list = ComputerMain.proListList;

	@Override
	protected void map(Text key, ComputerWritable value, Context context) throws IOException, InterruptedException {
		//从数据库里读取数据并得到省份写入reduce
		String city = key.toString();
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).getCity().size(); j++) {
				if (city.equals(list.get(i).getCity().get(j))) {
					city = list.get(i).getPro();
					break;
				}
			}
		}
		keyOut.set(city);
		context.write(keyOut, value);
	}


}
