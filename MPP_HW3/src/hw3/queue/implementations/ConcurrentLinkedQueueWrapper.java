package hw3.queue.implementations;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWrapper<T> extends ConcurrentLinkedQueue<T>
		implements Queue<T> {

	@Override
	public void enqueue(T t) {
		this.add(t);
	}

	@Override
	public void preFill(int j, T nextInt){
		this.enqueue(nextInt);
	}

	@Override
	public T dequeue() {
		return this.remove();
	}

}
