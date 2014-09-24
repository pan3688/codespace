package hw2.edu.vt.ece.locks;

import hw2.edu.vt.ece.bench.TestThread;

public class Peterson implements Lock{

	private volatile boolean flag[] = new boolean[2];
	private volatile int victim;
	
	/*
	 * (non-Javadoc)
	 * @see hw2.edu.vt.ece.locks.Lock#lock()
	 * To comply with Lock Interface
	 */
	public void lock(){
		int i = ((TestThread)Thread.currentThread()).getThreadId();
		int j = 1-i;
		flag[i] = true;
		victim = i;
		while(flag[j] && victim == i);
//			System.out.println("Thread " + i + " waiting");
	}
	
	/*
	 * Actual locking method
	 */
	public void lock(int i) {
//		int i = ((TestThread)Thread.currentThread()).getThreadId();
		int j = 1-i;
		flag[i] = true;
		victim = i;
		while(flag[j] && victim == i);
//			System.out.println("Thread " + i + " waiting");
	}

	/*
	 * (non-Javadoc)
	 * @see hw2.edu.vt.ece.locks.Lock#unlock()
	 * To comply with Lock Interface
	 */
	public void unlock(){
		int i = ((TestThread)Thread.currentThread()).getThreadId();
		flag[i] = false;
	}
	
	/*
	 * Actual unlock method
	 */
	public void unlock(int i) {
//		int i = ((TestThread)Thread.currentThread()).getThreadId();
		flag[i] = false;
	}

}
