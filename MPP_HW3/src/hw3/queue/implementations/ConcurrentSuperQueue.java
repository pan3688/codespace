package hw3.queue.implementations;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ConcurrentSuperQueue<T> implements Queue<T> {

	private java.util.Queue<T>[] queue = null;
	private final int subqueue_capacity = 1000;
	
	public ConcurrentSuperQueue(Integer N) {
		
		queue = (java.util.Queue<T> []) new java.util.concurrent.ConcurrentLinkedQueue[N];
		
		for(int i=0;i<N;i++)
			queue[i] = new ConcurrentLinkedQueue<T>();
		
	}
	
	@Override
	public void enqueue(T t) {
		int n = ThreadLocalRandom.current().nextInt() % queue.length;
		if(n<0)	n=-n;
		
		queue[n].add(t);
	}

	@Override
	public T dequeue() {
		int n = ThreadLocalRandom.current().nextInt() % queue.length;
		if(n<0)	n=-n;
		
		return queue[n].poll();
	}

}
