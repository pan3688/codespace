package hw3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

import hw3.queue.implementations.Queue;

public class Test {

	private static final int threadCount = 2;
	private static final int N = 8;
	
	private static final String seqSuper = "SequentialSuperQueue";
	private static final String conSuper = "ConcurrentSuperQueue";
	private static Thread[] myThreads = null;
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
				InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String queue = (args.length!=0) ? args[0] : seqSuper;
		
		Class<Queue> myClass = (Class<Queue>) Class.forName("hw3.queue.implementations." + queue);
		Constructor<Queue> myConstructor = myClass.getConstructor(Integer.class);
		Queue<Integer> q = (Queue<Integer>)myConstructor.newInstance(N);
		
		ThreadLocalRandom mainRandom = ThreadLocalRandom.current();
		
		for(int i = 0;i<100000; i++){
			try {
				q.enqueue(mainRandom.nextInt());
			} catch (Exception e) {
			}
		}
		
		myThreads = new Thread[N];
		
		for(int j = 0;j<threadCount;j++)
			myThreads[j] = new TestThread(q);
		
		for(int k = 0;k<threadCount;k++)
			myThreads[k].start();
		
		for(int l = 0;l<threadCount;l++){
			try {
					myThreads[l].join();
					System.out.println("Thread\t" + myThreads[l].getId() +"\tOperation#\t" + ((TestThread)myThreads[l]).getOps());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
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
}
