package hw3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

import hw3.queue.implementations.Queue;

public class Test {

	private static int threadCount = 64;
	private static int N = 2;
	private static volatile long totalOps = 0L;
	
	private static final String seqSuper = "SequentialSuperQueue";
	private static final String conSuper = "ConcurrentSuperQueue";
	private static Thread[] myThreads = null;
	
	public static void test(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
				InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		totalOps = 0L;
		
		String queue = (args.length!=0) ? args[0] : seqSuper;
		threadCount = (args.length != 0) ? Integer.parseInt(args[1]) : 2;
		N = (args.length != 0) ? Integer.parseInt(args[2]) : 2;
	
		Class<Queue> myClass = (Class<Queue>) Class.forName("hw3.queue.implementations." + queue);
		
		/*for(Constructor<?> con : myClass.getConstructors())
			System.out.println(con);*/
		
		Constructor<Queue> myConstructor = myClass.getConstructor(Integer.class);
		Queue<Integer> q = (Queue<Integer>)myConstructor.newInstance(N);
		
		ThreadLocalRandom mainRandom = ThreadLocalRandom.current();
		
		for(int i = 0;i<100000; i++){
			try {
				q.enqueue(mainRandom.nextInt());
			} catch (Exception e) {
			}
		}
		
		myThreads = new Thread[threadCount];
		
		for(int j = 0;j<threadCount;j++)
			myThreads[j] = new TestThread(q);
		
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
		System.out.println("ThreadCount\t" + threadCount + "\tTotal Operations\t" + totalOps +"\tThroughput\t" + totalOps/(5*threadCount));
		/*
		 * to test the Queues
		 */
		/*for(int i = 0;i<200;i++){
			Object tail = null;
			try {
				tail = q.dequeue();
				
				if(tail != null)
					System.out.println(tail);
			} catch (Exception e) {
				System.out.println("Subqueue seems EMP");
			}
		}*/
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String subqueue_array[] = { seqSuper, conSuper };
		int N_array[] = { 2,8};
		int threadCountArray[] = { 2,4,8,16,32,48,64};
		
		for(String sub : subqueue_array){
			for(int i : N_array){
				System.out.println(sub + "\tN = " + i);
				for(int j : threadCountArray){
					String args_test[] = {sub,j+"",i+""};
					for(int k=0;k<5;k++){
						test(args_test);
					}
				}
			}
		}
	}
}
