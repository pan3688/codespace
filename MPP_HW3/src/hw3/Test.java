package hw3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

import hw3.queue.implementations.ConcurrentLinkedQueueWrapper;
import hw3.queue.implementations.Queue;
import hw3.queue.implementations.SimpleQueue;

public class Test {

	private static int threadCount = 2;
	private static int N = 2;
	private static volatile long totalOps = 0L;
	
	private static final String seqSuper = "hw3.queue.implementations.SequentialSuperQueue";
	private static final String conSuper = "hw3.queue.implementations.ConcurrentSuperQueue";
	private static final String simpleBaseQueue = "hw3.queue.implementations.SimpleQueue";
	private static final String singleConcurrentQueue = "hw3.queue.implementations.ConcurrentLinkedQueueWrapper";
	
	private static Thread[] myThreads = null;
	private static ThreadLocalRandom mainRandom = ThreadLocalRandom.current();
	
	public static void test(String[] args) throws Exception {
		
		String queue = (args.length!=0) ? args[0] : seqSuper;
		threadCount = (args.length != 0) ? Integer.parseInt(args[1]) : 2;
		N = (args.length != 0) ? Integer.parseInt(args[2]) : 2;
	
		Class<Queue> myClass = (Class<Queue>) Class.forName(queue);
		
		Constructor<Queue> myConstructor = myClass.getConstructor(Integer.class);
		Queue<Integer> q = (Queue<Integer>)myConstructor.newInstance(N);
		
		for(int j=0;j<N;j++){
			for(int i = 0;i<10000; i++){
				try {
					q.preFill(j,mainRandom.nextInt());
				} catch (Exception e) {
				}
			}
		}
		threadOps(q,threadCount);
	}

	private static void threadOps(Queue<Integer> q,int threadCount) {
		totalOps = 0L;
		
		myThreads = new Thread[threadCount];
		
		for(int j = 0;j<threadCount;j++)
			myThreads[j] = new TestThread(q);
		
		for(int k = 0;k<threadCount;k++)
			myThreads[k].start();
		
		for(int l = 0;l<threadCount;l++){
			try {
					myThreads[l].join();
					totalOps = totalOps +  ((TestThread)myThreads[l]).getOps();
			} catch (InterruptedException e) {
			//	e.printStackTrace();
			}
		}
		System.out.println(totalOps/(5000));
	}
	
	public static void main(String[] args) throws Exception {
		
		String queue_array[] = { conSuper };
		int N_array[] = {32,64,256};
		int threadCountArray[] = { 1,2,4,6,8,12,16};
		
		for(String sub : queue_array){
			for(int j : threadCountArray){
				System.out.println(sub + ",threadCount = " + j);
				
				if(sub.equals(simpleBaseQueue)){
					for(int k=0;k<3;k++){
						hw3.queue.implementations.Queue<Integer> q = new SimpleQueue<>(100000);
						
						for(int i = 0;i<10000;i++){
							q.preFill(0, mainRandom.nextInt());
						}
						threadOps(q, j);
					}
				}else if(sub.equals(singleConcurrentQueue)){
					for(int k=0;k<3;k++){
						hw3.queue.implementations.Queue<Integer> q = new ConcurrentLinkedQueueWrapper<>();
						
						for(int i = 0;i<10000;i++){
							q.preFill(0, mainRandom.nextInt());
						}
						threadOps(q, j);
					}
				}else{
					for(int i : N_array){
						System.out.println("N\t" + i + "\tThroughput");
						String args_test[] = {sub,j+"",i+""};
						for(int k=0;k<3;k++){
							test(args_test);
						}
					}
				}
			}
		}
	}
}
