package edu.vt.ece.locks;

import edu.vt.ece.bench.TestThread;

public class LockTwo implements Lock{

	private volatile int victim;
	
	@Override
	public void lock() {
		int i = ((TestThread)Thread.currentThread()).getThreadId();
		victim = i;
		while(victim == i);
//			System.out.println("Thread " + i + " waiting");
	}

	@Override
	public void unlock() {}

}
