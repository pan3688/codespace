package hw2.edu.vt.ece.locks;

import hw2.edu.vt.ece.bench.TestThread;

public class LockOne implements Lock{

	/*
	 * fixed
	 * flag array should be volatile -- Programming Assignment Question 1
	 */
	private volatile boolean[] flag = new boolean[2];
	
	@Override
	public void lock() {
		int i = ((TestThread)Thread.currentThread()).getThreadId();
		int j = 1 - i;
		flag[i] = true;
		while(flag[j]);
//			System.out.println("Thread " + i + " waiting");
	}

	@Override
	public void unlock() {
		int i = ((TestThread)Thread.currentThread()).getThreadId();
		flag[i] = false;
	}

}
