package hw3.queue.implementations;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ConcurrentSuperQueue<T> implements Queue<T> {

	private java.util.Queue<T>[] queue = null;
	private int mask = 0;
	
	public ConcurrentSuperQueue(Integer N) {
		
		queue = (java.util.Queue<T> []) new java.util.concurrent.ConcurrentLinkedQueue[N];
		
		for(int i=0;i<N;i++)
			queue[i] = new ConcurrentLinkedQueue<T>();
		
		/*
		 * mask will be used to find, 1 subqueue out of N subqueus,
		 * to enqueue an item or dequeue an item from
		 */
		mask = (int)N - 1;
	}
	
	@Override
	public void enqueue(T t) {
		int n = ThreadLocalRandom.current().nextInt() & mask ;
		
		queue[n].add(t);
	}

	@Override
	public T dequeue() {
		int n = ThreadLocalRandom.current().nextInt() & mask ;
		
		return queue[n].poll();
	}

}
