package hw4.prioritylocks;

import hw4.locks.Lock;

import java.util.Random;

public class Test {

	private static final int threadCount = 8;
	
	public static void main(String[] args) {
		final Lock lock = new PriorityCLH();
		final Random random = new Random();
		final Counter counter = new SharedCounter(0, lock);
		
		for(int i = 0;i < threadCount;i++){
			new TestThread(counter,random.nextInt(5)+1).start();
		}
	}

}
