package hw4.barriers_2;

public class TestThread extends Thread {

	private static int ID_GEN = 0;
	private static final int thread_count=8;

	private int id;
	private Lock lock;
	private FooBar fb;
	
	private static volatile int[] b = new int[thread_count];

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
		do{
			if(this.getThreadId()==0)
				b[0] = 1;
			else if(b[this.getThreadId()-1] == 1)
				b[this.getThreadId()] = 1;
		}while(b[this.getThreadId()] == 0);
		
		if(this.getThreadId() == thread_count-1) b[this.getThreadId()] = 2;
		
		while(!(b[thread_count-1] == 2));

		// Barrier ends
		
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
