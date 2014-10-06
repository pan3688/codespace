package hw3;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

import hw3.queue.implementations.Queue;

public class TestThread extends Thread {

	private Queue<Integer> q;
	private long ops;
	private ThreadLocalRandom myRGN = ThreadLocalRandom.current();
	
	public TestThread(Queue<Integer> q) {
		this.q = q;
		ops = 0;
	}
	
	@Override
	public void run() {
		
		long start = System.currentTimeMillis();
		long current = 0L;
		long tempCount = 0L;
		
		do{
			
			try {
				doOperation(myRGN.nextInt());
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			current = System.currentTimeMillis();
		}while((current-start) < 10000);
		
		start = System.currentTimeMillis();
		do{
			while(tempCount < 1000000){
				try {
					doOperation(myRGN.nextInt());
					ops++;
				} catch (Exception e) {
					e.printStackTrace();
				}
				tempCount++;
			}
			tempCount = 0L;
			current = System.currentTimeMillis();
		}while((current-start) < 5000);
	}

	public long getOps() {
		return ops;
	}
	
	private void doOperation(int item) throws Exception{
		
		if((item & 1) == 0)
			q.enqueue(item);
		else
			q.dequeue();
			
	}
}
