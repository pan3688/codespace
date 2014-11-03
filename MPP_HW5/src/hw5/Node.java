package hw5;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class Node<T> {

	T item;
	int key;
	volatile AtomicMarkableReference<Node<T>> next;
	
	public Node(T item) {
		this.item = item;
		
		if(item != null)
			this.key = item.hashCode();
		else
			this.key = -1;	//	since the range is 0-99,
							//	we can set hashCode of the head,which is null,to be -1
							//	however hash code of a -1 would be -1,
							//	hence -1 can not be an element of the given list
	}
	
}
