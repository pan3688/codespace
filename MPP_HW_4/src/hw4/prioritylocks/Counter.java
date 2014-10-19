package hw4.prioritylocks;

/**
 * 
 * @author Mohamed M. Saad
 */
public class Counter {
	private int value;
	public Counter(int c){
		value = c;
	}
	public int getAndIncrement(){
		System.out.println("Thread " + ((TestThread)Thread.currentThread()).getThreadId() + " Priority " + 
											((TestThread)Thread.currentThread()).getThreadPriority() +" value " + value);
		int temp = value;
		value = temp + 1;
		return temp;
	}
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
