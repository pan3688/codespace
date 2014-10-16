package hw3.queue.implementations;

import java.util.concurrent.ThreadLocalRandom;

public class SequentialSuperQueue<T> implements Queue<T> {

	private Queue<T>[] subqueus = null;
	private final int subqueue_capacity = 1000000;
	private int mask = 0;
	
	public SequentialSuperQueue(Integer N) {
		subqueus = new SimpleQueue[N];
		
		for(int i=0;i<N;i++){
			subqueus[i]= new SimpleQueue<T>(subqueue_capacity);
		}
		mask = (int)N - 1;
	}
	
	public void preFill(int queIndex,T item) throws Exception{
		subqueus[queIndex].enqueue(item);
	}
	
	@Override
	public void enqueue(T t) {
		int n = ThreadLocalRandom.current().nextInt() & mask;

		try {
			subqueus[n].enqueue(t);
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	@Override
	public T dequeue() {
		int n = ThreadLocalRandom.current().nextInt() & mask;
		
		T t = null;
		
		try {
			t = (T) subqueus[n].dequeue();
		} catch (Exception e) {
//			e.printStackTrace();
		}
			
		return t;
	}
}