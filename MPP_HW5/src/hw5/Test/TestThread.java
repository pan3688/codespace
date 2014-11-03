package hw5.Test;

import java.util.concurrent.ThreadLocalRandom;

import hw5.Set;

public class TestThread extends Thread {

	public static volatile boolean warm_up = true;
	public static volatile boolean run_up = true;
	
	private Set<Integer> mySet;
	private long operations = 0L;
	
	long ops_true = 0L;
	long ops_false = 0L;
	
	private final ThreadLocalRandom myRandomOp = ThreadLocalRandom.current();
	
	public TestThread(Set<Integer> mySet) {
		this.mySet = mySet;
	}
	
	public void run() {
	//	int i;
		System.out.println("Thread " + getId() +" is starting");
		while(warm_up){
			
			/*i = myRandomOp.nextInt();
			mySet.contains(i & 100);
			if((i & 10) < 8) 
				mySet.contains(i & 100);
			else if((i & 10) == 8)
				mySet.add(i & 100);
			else
				mySet.remove(i & 100);*/
			
			for(int i = 0;i<100;i++)
				mySet.add(i);
			
		}
		
		System.out.println("Thread " + getId() +" is running");
		while(run_up){
		/*//	i = myRandomOp.nextInt();
			
			if(mySet.contains(i & 100))
				ops_true ++;
			else
				ops_false++;
			if((i & 10) < 8) 
				mySet.contains(i & 100);
			else if((i & 10) == 8)
				mySet.add(i & 100);
			else
				mySet.remove(i & 100);*/
			
			for(int i = 0;i<100;i++){
				if(mySet.add(i))
					ops_true++;
				else
					ops_false++;
				
				operations++;
			}
			
		}
		System.out.println("Thread " + getId() +" is stopping");
	}
	
	public long getOperations() {
		return operations;
	}
}
