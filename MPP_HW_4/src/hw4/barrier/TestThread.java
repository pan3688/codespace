package hw4.barrier;

import hw4.locks.Lock;
import hw4.locks.TTASLock;

public class TestThread extends Thread {

	private static final int thread_count=8;
	
	private static int ID_GEN = 0;
	private static volatile int counter=0;
	private static volatile int[] b = new int[thread_count];
	private static enum Barrier { LockBased,ArrayBased };
	
	private int id;
	private Lock lock;
	private FooBar fb;
	private Barrier barrier;

	public TestThread(Lock lock,FooBar fb,Barrier barrier) {
		id = ID_GEN++;
		this.lock=lock;
		this.fb=fb;
		this.barrier = barrier;
	}
	
	@Override
	public void run() {
		
		// Thread # calling FOO method
		fb.foo();
		
		// Barrier Begins
		if(barrier.equals(Barrier.LockBased)){
			
			lock.lock();
			try{	
				counter++;
	//			System.out.println("thread " + this.id + " Counter = " + counter);
			}finally{
				lock.unlock();
			}
			
			while(counter!=thread_count);
			
		}else if(barrier.equals(Barrier.ArrayBased)){
			
			do{
				if(this.getThreadId()==0)
					b[0] = 1;
				else if(b[this.getThreadId()-1] == 1)
					b[this.getThreadId()] = 1;
			}while(b[this.getThreadId()] == 0);
			
			if(this.getThreadId() == thread_count-1)
				b[this.getThreadId()] = 2;
			
			while(!(b[thread_count-1] == 2));
		}
		//Barrier Ends
		
		// Thread # calling bar method
		fb.bar();
	}
	
	public int getThreadId() {
		return id;
	}
	
	public static void main(String[] args) {
		Lock lock = new TTASLock();
		FooBar fb = new FooBar();
		
		for(int i=0;i<thread_count;i++)
			new TestThread(lock, fb,Barrier.LockBased).start();
	}
}
