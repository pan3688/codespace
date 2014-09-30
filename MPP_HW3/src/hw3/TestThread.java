package hw3;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

import hw3.queue.implementations.Queue;

public class TestThread extends Thread {

	private static int thread_id_seed = 0;
	
	private Queue<Integer> q;
	private int id;
	private int ops;
	
	public TestThread(Queue<Integer> q) {
		this.q = q;
		id = thread_id_seed++;
		ops = 0;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		Calendar myCalendar = Calendar.getInstance();
		ThreadLocalRandom myRGN = ThreadLocalRandom.current();
		
		long start = myCalendar.getTimeInMillis();
		long current = 0L;
		
		do{
			
			
			current = myCalendar.getTimeInMillis();
		}while((current-start)/1000 < 5);
		
		
		start = myCalendar.getTimeInMillis();
		do{
			
			
			current = myCalendar.getTimeInMillis();
		}while((current-start)/1000 < 5);
		
	}
	
}
