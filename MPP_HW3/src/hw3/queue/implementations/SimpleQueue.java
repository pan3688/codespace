package hw3.queue.implementations;

public class SimpleQueue<T> implements Queue<T>{

	private int head;
	private int tail;
	private T[] items;
	
	public SimpleQueue(int capacity) {
		head = 0;
		tail = 0;
		items = (T[]) new Object[capacity];
	}
	
	@Override
	public void enqueue(T t) throws Exception {
		if(tail-head == items.length)
			throw new Exception();
		
		items[tail % items.length] = t;
		
		tail++;
	}

	@Override
	public T dequeue() throws Exception {
		if(tail==head)
			throw new Exception();
		
		T t = items[head % items.length];
		
		head++;
		
		return t;
	}

	/*
	 * to test the simple queue
	 */
	public static void main(String[] args) throws Exception {
		
		Queue<Integer> test = new SimpleQueue<Integer>(20);
		
		for(int i = 0;i<20;i++)
			test.enqueue(i);
		
		for(int i =0; i< 15;i++)
			System.out.println(test.dequeue());
		
	}
}
