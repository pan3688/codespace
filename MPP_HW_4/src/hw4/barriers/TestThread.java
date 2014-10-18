package hw4.barriers;

public class TestThread extends Thread {

	private static int ID_GEN = 0;
	private static final int thread_count=32;

	private int id;
	private Lock lock;
	private FooBar fb;
	
	private static volatile int counter=0;

	public TestThread(Lock lock,FooBar fb) {
		id = ID_GEN++;
		this.lock=lock;
		this.fb=fb;
	}
	
	@Override
	public void run() {
		
		// Thread # calling FOO method
		fb.foo();
		
		// Barrier Begins
		lock.lock();
		counter++;
//		System.out.println("thread " + this.id + " Counter = " + counter);
		lock.unlock();
		
		while(counter!=thread_count);
		//Barrier Ends
		
		// Thread # calling bar method
		fb.bar();
	}
	
	public int getThreadId() {
		return id;
	}
	
	public static void main(String[] args) {
		Lock lock = new Lock();
		FooBar fb = new FooBar();
		
		for(int i=0;i<thread_count;i++)
			new TestThread(lock, fb).start();
	}
}
