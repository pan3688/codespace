package hw2;

import edu.vt.ece.bench.TestThread;

public class Peterson implements Lock{

	private volatile boolean flag[] = new boolean[2];
	private volatile int victim;
	
	@Override
	public void lock() {
		int i = ((TestThread)Thread.currentThread()).getThreadId();
		int j = 1-i;
		flag[i] = true;
		victim = i;
		while(flag[j] && victim == i);
//			System.out.println("Thread " + i + " waiting");
	}

	@Override
	public void unlock() {
		int i = ((TestThread)Thread.currentThread()).getThreadId();
		flag[i] = false;
	}

}
