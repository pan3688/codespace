package hw5.Test;

import hw5.LockFreeList;
import hw5.Set;

public class Test {

	private static int thread_count = 2;
	
	public static void main(String[] args) throws InterruptedException {
		
		Set<Integer> testSet = new LockFreeList<Integer>(false);
		TestThread[] testThreads = new TestThread[thread_count];
		
		for(int i=0;i<thread_count;i++){
			testThreads[i] = new TestThread(testSet);
			testThreads[i].start();
		}
		
		long total = 0L;
		
		for(int i=0;i<thread_count;i++){
			testThreads[i].join();
			total += testThreads[i].getOperations();
		}
		
		System.out.println("Total Operations Count:\t" + total/5);
	}
	
	/*public static void main(String[] args) {
		
		Set<Integer> intSet = new LockFreeList<Integer>(false);
	
		System.out.println("Hash#" + new Integer(-1).hashCode());
		System.out.println(intSet.contains(-1));
		System.out.println(intSet.remove(0));
		System.out.println(intSet.add(0));
		System.out.println(intSet.add(88));
		System.out.println(intSet.add(5));
		System.out.println(intSet.add(56));
		
		System.out.println("Contains\t" + intSet.contains(0));
		System.out.println("Contains\t" + intSet.contains(9));
		System.out.println(intSet.add(5));
		System.out.println(intSet.add(99));
		System.out.println("Remove\t"+ intSet.remove(0));
		System.out.println(intSet.contains(0));
		System.out.println(intSet.remove(-1));
		
		System.out.println("Contains\t" + intSet.contains(9));
		
		System.out.println("Add \t" + intSet.add(9));
		
		System.out.println("Contains\t" + intSet.contains(9));
		
		System.out.println("Add \t" + intSet.add(1));
		
		System.out.println("Remove\t" + intSet.remove(9));
		
		System.out.println("Contains\t" + intSet.contains(9));
		
		System.out.println("Contains\t" + intSet.contains(1));
		
		for(int i =100;i<110;i++)
			System.out.println(intSet.contains(i));
		System.out.println("");
		for(int i =100;i<110;i++)
			System.out.println(intSet.remove(i));
		System.out.println("");
		for(int i =100;i<110;i++)
			System.out.println(intSet.add(i));
		System.out.println("");
		for(int i =100;i<110;i++)
			System.out.println(intSet.contains(i));
		System.out.println("");
		for(int i =100;i<110;i++)
			System.out.println(intSet.remove(i));
		System.out.println("");
		for(int i =100;i<110;i++)
			System.out.println(intSet.contains(i));
	}*/
}
