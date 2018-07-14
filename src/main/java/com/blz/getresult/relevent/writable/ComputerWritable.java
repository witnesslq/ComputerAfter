package com.blz.getresult.relevent.writable;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ComputerWritable implements WritableComparable<ComputerWritable> {
	private double releventNum;
	private int lowMoney;
	private int higMoney;

	public void set(double releventNum, int lowMoney, int higMoney) {
		this.releventNum = releventNum;
		this.lowMoney = lowMoney;
		this.higMoney = higMoney;
	}

	public double getReleventNum() {
		return releventNum;
	}

	public void setReleventNum(double releventNum) {
		this.releventNum = releventNum;
	}

	public int getLowMoney() {
		return lowMoney;
	}

	public void setLowMoney(int lowMoney) {
		this.lowMoney = lowMoney;
	}

	public int getHigMoney() {
		return higMoney;
	}

	public void setHigMoney(int higMoney) {
		this.higMoney = higMoney;
	}

	@Override
	public int compareTo(ComputerWritable o) {
		if (releventNum != o.getReleventNum()) {
			return releventNum < o.releventNum ? -1 : 1;
		} else if (lowMoney != o.getLowMoney()) {
			return lowMoney < o.lowMoney ? -1 : 1;
		} else if (higMoney != o.getHigMoney()) {
			return higMoney < o.higMoney ? -1 : 1;
		} else {
			return 0;
		}
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeDouble(releventNum);
		dataOutput.writeInt(lowMoney);
		dataOutput.writeInt(higMoney);
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		releventNum = dataInput.readDouble();
		lowMoney = dataInput.readInt();
		higMoney = dataInput.readInt();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ComputerWritable)) {
			return false;
		}
		ComputerWritable computerWritable = (ComputerWritable) obj;
		return releventNum == computerWritable.releventNum &&
				lowMoney == computerWritable.lowMoney &&
				higMoney == computerWritable.higMoney;
	}

	@Override
	public int hashCode() {
		return (int) (releventNum * 157 + lowMoney + higMoney);
	}
}
