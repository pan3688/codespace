package hw3;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

import hw3.queue.implementations.Queue;

public class TestThread extends Thread {

//	private static int thread_id_seed = 0;
	
	private Queue<Integer> q;
//	private int id;
	private int ops;
	private ThreadLocalRandom myRGN = ThreadLocalRandom.current();
	
	public TestThread(Queue<Integer> q) {
		this.q = q;
//		id = thread_id_seed++;
		ops = 0;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		long start = System.currentTimeMillis();
		long current = 0L;
		
		do{
			try {
				doOperation();
			} catch (Exception e) {
				e.printStackTrace();
			}
			current = System.currentTimeMillis();
		}while((current-start)/1000 < 5);
		
		start = System.currentTimeMillis();
		do{
			try {
				doOperation();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ops++;
			current = System.currentTimeMillis();
		}while((current-start)/1000 < 2);
	}
	

	/*public long getId() {
		return id;
	}*/

	public int getOps() {
		return ops;
	}
	
	private void doOperation() throws Exception{
		
		
		int enqDeq = myRGN.nextInt() % 2;
		if(enqDeq < 0) enqDeq= -enqDeq;
			
		
			if(enqDeq == 0)
				q.enqueue(myRGN.nextInt());
			else
				q.dequeue();
		
			
	}
	
}
