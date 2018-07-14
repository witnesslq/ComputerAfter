package com.blz.start;

import com.blz.getresult.sun.main.SunComputerMain;

public class Thread2 extends Thread {
	@Override
	public void run() {
		try {
			new SunComputerMain().SunComputerMainStart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
