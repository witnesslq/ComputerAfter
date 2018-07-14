package com.blz.start;

import com.blz.relevance.Relevance;

import java.io.IOException;

public class Thread3 extends Thread {
	@Override
	public void run() {
		try {
			new Relevance().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
