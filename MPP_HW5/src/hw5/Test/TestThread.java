package hw5.Test;

import java.util.concurrent.ThreadLocalRandom;

import hw5.Set;

public class TestThread extends Thread {

	private static boolean warm_up = true;
	
	private Set<Integer> mySet;
	private long operations = 0L;
	
//	long ops_true = 0L;
//	long ops_false = 0L;
	
	private final ThreadLocalRandom myRandomOp = ThreadLocalRandom.current();
	
	public TestThread(Set<Integer> mySet) {
		this.mySet = mySet;
	}
	
	public void run() {
		int j;
		System.out.println("Thread " + getId() +" is starting");
		
		long temp = 0L;
		long start = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - start < 10000){
			
			temp = 0L;
			while(temp<10000){
				setOperations();
				temp++;
			}
		}
		warm_up = false;
		System.out.println("Thread " + getId() +" is running");
		
		start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < 5000){
			
			temp = 0L;
			while(temp<10000){
				setOperations();
				temp++;
			}
		}
	//	System.out.println("Thread " + getId() +" is stopping");
	}

	private void setOperations() {
		int j;
		
		/*
		 *  will help to maintan contain/add/remove operations ratio
		 *  one method call = 100 operations
		 */
		for(int i = 0;i<10;i++){
			j = myRandomOp.nextInt() & 99; 			// ensures range between 0-99
			
			if(i<8){								
				mySet.contains(j);					//System.out.println("Contains");
			}else if(i<9){
				mySet.add(j);						//System.out.println("add");
			}else{
				mySet.remove(j);					//System.out.println("remove");
			}
			if(!warm_up)
				operations++;
		}
	}
	
	public long getOperations() {
		return operations;
	}
}
