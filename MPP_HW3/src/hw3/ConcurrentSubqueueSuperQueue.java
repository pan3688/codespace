package hw3;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ConcurrentSubqueueSuperQueue<T> implements Queue<T> {

	private java.util.Queue<T>[] queue = null;
	private final int subqueue_capacity = 1000;
	
	public ConcurrentSubqueueSuperQueue(int N) {
		// TODO Auto-generated constructor stub
		
		queue = (java.util.Queue<T> []) new java.util.concurrent.ConcurrentLinkedQueue[N];
		
		for(int i=0;i<N;i++)
			queue[i] = new ConcurrentLinkedQueue<T>();
		
	}
	
	@Override
	public void enqueue(T t) {
		int n = ThreadLocalRandom.current().nextInt() % queue.length;
		
		queue[n].add(t);
	}

	@Override
	public T dequeue() {
		int n = ThreadLocalRandom.current().nextInt() % queue.length;
		
		return queue[n].poll();
	}

}
