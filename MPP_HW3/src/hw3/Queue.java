package hw3;

public interface Queue<T> {

	public void enqueue(T t) throws Exception;
	public T dequeue() throws Exception;
	
}
