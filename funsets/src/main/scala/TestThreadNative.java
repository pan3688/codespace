

import hw3.queue.implementations.Queue;
import hw3.queue.implementations.SimpleQueue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class TestThreadNative extends Thread{
	private Queue<Integer> q;
	private long ops;
	private ThreadLocalRandom myRGN = ThreadLocalRandom.current();
	
	public TestThreadNative(Queue<Integer> q) {
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
//				e.printStackTrace();
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
	//				e.printStackTrace();
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
		
		if((item & 1) == 0){
			/*q.enqueue(item);*/
			if(q instanceof SimpleQueue<?>)
				q.enqueue(item);
			else if(q instanceof ConcurrentLinkedQueue)
				((ConcurrentLinkedQueue<Integer>)q).add(item);
		}else{
			if(q instanceof SimpleQueue<?>)
				q.dequeue();
			else if(q instanceof ConcurrentLinkedQueue)
				((ConcurrentLinkedQueue<Integer>)q).remove();
		}
	}
}
