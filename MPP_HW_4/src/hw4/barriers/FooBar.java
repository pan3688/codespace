package hw4.barriers;

public class FooBar {

	public void foo(){
		
		System.out.println("Thread " + ((TestThread) Thread.currentThread()).getThreadId() + " executing foo...");
		
	}
	
	public void bar(){
		
		System.out.println("Thread " + ((TestThread) Thread.currentThread()).getThreadId() + " executing bar...");
		
	}
}
