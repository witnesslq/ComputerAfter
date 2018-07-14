package com.blz.getresult.sun.writable;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SunComputerWritable implements WritableComparable<Object> {
	private int money;

	public void set(int money) {
		this.money = money;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(money);
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		money = dataInput.readInt();
	}
}
