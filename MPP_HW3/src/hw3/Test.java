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
		
		for(int j=0;j<N;j++){
			for(int i = 0;i<100000; i++){
				try {
					q.preFill(j,mainRandom.nextInt());
				} catch (Exception e) {
				}
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
		System.out.println(threadCount + "," + totalOps/(5000));
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String subqueue_array[] = { seqSuper, conSuper };
		int N_array[] = { 2,8};
	//	int threadCountArray[] = { 1,2,4,6,8,12,16,20,24,32,40,48,64};
		int threadCountArray[] = {1,6,12,20,24,40};
		
		for(String sub : subqueue_array){
			for(int i : N_array){
				System.out.println(sub + ",N = " + i);
				for(int j : threadCountArray){
					System.out.println("ThreadCount"  + ",Throughput");
					String args_test[] = {sub,j+"",i+""};
					for(int k=0;k<5;k++){
						test(args_test);
					}
				}
			}
		}
	}
}
