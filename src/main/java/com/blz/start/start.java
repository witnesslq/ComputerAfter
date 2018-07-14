package com.blz.start;

public class start {
	public int start() throws InterruptedException {
		Thread1 thread1 = new Thread1();
		Thread2 thread2 = new Thread2();
		//Thread3 thread3 = new Thread3();

		thread1.start();
		//thread1.join();
		//thread2.start();
		//thread2.join();
		//thread3.start();
		//thread3.join();

		return 0;
	}

	public static void main(String[] args) throws InterruptedException {
		new start().start();
	}
}
