package com.blz.start;

import com.blz.getresult.relevent.main.ComputerMain;

public class Thread1 extends Thread {
	@Override
	public void run() {
		try {
			new ComputerMain().startComputerMain();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
