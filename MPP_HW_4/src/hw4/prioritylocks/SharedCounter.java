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
	
	public int getAndIncrement() {
		lock.lock();
		int temp = -1;
		try {
			temp = super.getAndIncrement();
		} finally {
			lock.unlock();
		}
		return temp;
	}
}
