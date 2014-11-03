package hw4.prioritylocks;

import hw4.locks.CLH;
import hw4.locks.Lock;

import java.util.Random;

public class Test {

	private static final int threadCount = 24;
	
	public static void main(String[] args) throws InterruptedException {
		final Lock lock = new CLH();
		final Random random = new Random();
		final Counter counter = new SharedCounter(0, lock);
		
		TestThread[] myThreads = new TestThread[threadCount];
		
		for(int i = 0;i < threadCount;i++){
			myThreads[i]=new TestThread(counter,random.nextInt(5)+1);
		}
		for(int a=0;a<threadCount;a++){
			myThreads[a].start();
		}
		
		for(int j=0;j < threadCount;j++){
			myThreads[j].join();
		}
		
		double total_waiting = 0;
		for(int k=0;k < threadCount;k++){
			total_waiting += myThreads[k].getWaiting() * myThreads[k].getThreadPriority();
		}
		System.out.println(threadCount + "\t" + total_waiting/threadCount);
	}

}
