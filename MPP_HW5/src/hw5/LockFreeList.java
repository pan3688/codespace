package hw5;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeList<T> implements Set<T> {

	boolean contains_cleanup;
	final Node<T> head;
	
	public LockFreeList(boolean contains_cleanup) {
		this.contains_cleanup = contains_cleanup;
		head = new Node<T>(null);
		head.next = new AtomicMarkableReference<Node<T>>(null,false);
	}
	
	public Window find(Node<T> head,int key){
		Node<T> pred = null, curr = null, succ = null;
		boolean[] marked = {false};
		boolean snip;
		
		retry: while(true){
			pred = head;
			
			if(pred.next.get(marked) == null)
				return new Window(pred,null);
			
			curr = (Node<T>) pred.next.getReference();
			
			while(true){
				
				if(curr == null)
					return new Window(pred,null);
				
				succ = (Node<T>) curr.next.get(marked);
				
				while(succ!=null && marked[0]){
					snip = pred.next.compareAndSet(curr, succ, false, false);
					if(!snip)
						continue retry;
					curr = succ;
					succ = (Node<T>) curr.next.get(marked);
				}
				if(curr.key >= key)
					return new Window(pred,curr);
				
				pred = curr;
				curr = succ;
			}
		}
	}
	
	public boolean add(T item) {
		int key = item.hashCode();
		while(true){
			Window window = find(head,key);
			Node<T> pred = window.pred;
			Node<T> curr = window.curr;
			
			if(curr!= null && curr.key == key){
				return false;
			}else{
				Node<T> node = new Node<T>(item);
				node.next = new AtomicMarkableReference<Node<T>>(curr,false);
				if(pred.next.compareAndSet(curr, node, false, false)){
					return true;
				}
			}
		}
	}
	
	public boolean remove(T item) {
		int key = item.hashCode();
		boolean snip;
		while(true){
			Window window = find(head,key);
			Node<T> pred = window.pred;
			Node<T> curr = window.curr;
			
			if(curr == null || curr.key != key){
				return false;
			}else{
				Node<T> succ = curr.next.getReference();
				snip = curr.next.compareAndSet(succ, succ, false, true);
				if(!snip)
					continue;
				
				pred.next.compareAndSet(curr, succ, false, false);
				return true;
			}
		}
	}
	
	public boolean contains(T item) {
		boolean[] marked = {false};
		int key = item.hashCode();
		Node<T> curr = head;
		
		if(contains_cleanup)
			find(head,key);
		
		while(curr.next.get(marked) != null && curr.key < key){
			curr = curr.next.getReference();
			Node<T> succ = curr.next.get(marked);
		}
		return (curr.key == key && !marked[0]);
	}
	
}
