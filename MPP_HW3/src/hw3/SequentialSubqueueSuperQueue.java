package hw3;

import java.util.concurrent.ThreadLocalRandom;

public class SequentialSubqueueSuperQueue<T> implements Queue<T> {

	private Queue<T>[] subqueus = null;
	private final int subqueue_capacity = 1000;
	
	public SequentialSubqueueSuperQueue(int N) {
		subqueus = new SimpleQueue[N];
		
		for(int i=0;i<N;i++)
			subqueus[i]= new SimpleQueue<T>(subqueue_capacity);
	}
	
	@Override
	public void enqueue(T t) {
		int n = ThreadLocalRandom.current().nextInt() % subqueus.length;
		
		synchronized (subqueus[n]) {
			try {
				subqueus[n].enqueue(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public T dequeue() {
		int n = ThreadLocalRandom.current().nextInt() % subqueus.length;
		
		T t = null;
		synchronized (subqueus[n]) {
			try {
				t = (T) subqueus[n].dequeue();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return t;
	}

}
