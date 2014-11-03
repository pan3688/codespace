package hw4.locks;

public class TestThread extends Thread {

	private static final int thread_count=64;
	private static volatile boolean warmup = true;
	private static volatile boolean startcount = true;
	
	private Lock lock;
	private long start;
	private double duration;
	private double count;
	
	public TestThread(Lock lock) {
		this.lock = lock;
		start = 0L;
		duration = 0L;
	}
	
	@Override
	public void run() {
		
		while(warmup)	emptyCS();
		
		start = System.currentTimeMillis();
		count = 0L;
		
		while(startcount) emptyCS();
		
		duration = (System.currentTimeMillis() - start)/count;
	}
	
	public void emptyCS(){
		lock.lock();
		
		try{
			
		}finally{
			lock.unlock();
		}
		count++; // will be thread specific
	}
	
	public static void main(String[] args) throws InterruptedException {
		Lock lock = new TTASLock();
		
		TestThread[] myThreads = new TestThread[thread_count];
		for(int i=0;i<thread_count;i++)
			myThreads[i] = new TestThread(lock);
		
		for(int j=0;j<thread_count;j++)
			myThreads[j].start();
		
		Thread.sleep(5000);
		warmup = false;
		Thread.sleep(2000);
		startcount = false;
		
		for(int k=0;k<thread_count;k++)
			myThreads[k].join();
		
		double total_duration = 0L;
		long counter = 0L;
		for(int l=0;l<thread_count;l++){
			total_duration += myThreads[l].duration;
			counter += myThreads[l].count;
//			System.out.println(myThreads[l].count);
		}
		System.out.println(thread_count + "\t" + total_duration +"\t" + counter);
	}
}
