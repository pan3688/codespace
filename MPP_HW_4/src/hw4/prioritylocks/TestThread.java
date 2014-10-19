package hw4.prioritylocks;

public class TestThread extends Thread {

	private static int ID_GEN = 0;
	private static final int threadIterations = 10;
	
	private int id;
	private int priority;
	private Counter counter;
	
	public TestThread(Counter counter) {
		this.id = ID_GEN++;
		this.priority = 5; // by default -- least priority
		this.counter = counter;
	}
	
	public TestThread(Counter counter,int priority) {
		this.id = ID_GEN++;
		this.priority = priority;
		this.counter = counter;
	}
	
	@Override
	public void run() {
		
		for(int i = 0;i<threadIterations;i++)
			counter.getAndIncrement();
		
		System.out.println("Thread " + id + " DONE.. <Counter:" + counter + ">");
	}
	
	public int getThreadId() {
		return this.id;
	}
	public int getThreadPriority(){
		return this.priority;
	}
	
}
