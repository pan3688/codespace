

import hw3.queue.implementations.Queue;
import hw3.queue.implementations.SimpleQueue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class TestNative {
	private static int threadCount = 64;
	private static int N = 2;
	private static volatile long totalOps = 0L;
	
	private static final String simpleNative = "hw3.queue.implementations.SimpleQueue";
	private static final String concurrentNative = "java.util.concurrent.ConcurrentLinkedQueue";
	private static Thread[] myThreads = null;
	
	public static void test(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
				InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		totalOps = 0L;
		
		String queue = (args.length!=0) ? args[0] : simpleNative;
		threadCount = (args.length != 0) ? Integer.parseInt(args[1]) : 2;
//		N = (args.length != 0) ? Integer.parseInt(args[2]) : 2;
		
		Object q = null;
		
		if(queue.equals(simpleNative))
			q=(hw3.queue.implementations.Queue<Integer>)new SimpleQueue<Integer>(5000000);
		else
			q=(java.util.Queue<Integer>)new ConcurrentLinkedQueue();
		
		ThreadLocalRandom mainRandom = ThreadLocalRandom.current();
		
			for(int i = 0;i<1000000; i++){
				try {
					if(q instanceof SimpleQueue<?>)
						((hw3.queue.implementations.Queue<Integer>)q).enqueue(i);
					else if(q instanceof ConcurrentLinkedQueue)
						((ConcurrentLinkedQueue<Integer>)q).add(i);
				} catch (Exception e) {
				}
			}
			
		myThreads = new Thread[threadCount];
		
		for(int j = 0;j<threadCount;j++)
			myThreads[j] = new TestThreadNative(q);
		
		for(int k = 0;k<threadCount;k++)
			myThreads[k].start();
		
		for(int l = 0;l<threadCount;l++){
			try {
					myThreads[l].join();
			//		System.out.println("Thread\t" + myThreads[l].getId() +"\tOperation#\t" + ((TestThread)myThreads[l]).getOps());
					totalOps = totalOps +  ((TestThread)myThreads[l]).getOps();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(threadCount + "," + totalOps/(5000));
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String subqueue_array[] = { simpleNative, concurrentNative };
//		int N_array[] = { 2,8};
		int threadCountArray[] = { 1,2,4,6,8,12,16,20,24,32,40,48,64};
		
		for(String sub : subqueue_array){
		//	for(int i : N_array){
				System.out.println(sub);
				for(int j : threadCountArray){
					System.out.println("ThreadCount"  + ",Throughput");
					String args_test[] = {sub,j+""};
					for(int k=0;k<1;k++){
						test(args_test);
					}
				}
		//	}
		}
	}
}
