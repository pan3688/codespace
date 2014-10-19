package hw4.prioritylocks;

import hw4.locks.Lock;

/**
 * 
 * @author Mohamed M. Saad
 */
public class SharedCounter extends Counter{
	private Lock lock;

	public SharedCounter(int c, Lock lock) {
		super(c);
		this.lock = lock;
	}
	
	@Override
	public int getAndIncrement() {
		lock.lock();
		int temp = -1;
		try {
//			System.out.println("Thread " + ((TestThread)Thread.currentThread()).getThreadId()+" in CS...");
			temp = super.getAndIncrement();
		} finally {
			lock.unlock();
		}
		return temp;
	}

}
