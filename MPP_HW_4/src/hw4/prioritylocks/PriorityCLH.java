package hw4.prioritylocks;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import hw4.locks.Lock;

public class PriorityCLH implements Lock{

	private final int initialCapacity = 256;
	
	private volatile PriorityBlockingQueue<PNode> lockQueue;
	private volatile AtomicReference<PNode> tail;
	volatile ThreadLocal<PNode> myNode;
	volatile ThreadLocal<PNode> myPred;
	
	public PriorityCLH() {
		
		tail = new AtomicReference<PNode>(null);
		
		lockQueue = new PriorityBlockingQueue<PNode>(initialCapacity,new Comparator<PNode>() {
			public int compare(PNode pn1, PNode pn2) {
				if(pn1.priority < pn2.priority)
					return -1;
				else if(pn1.priority > pn2.priority)
					return 1;
				
				return 0;
			}
		});
		myNode = new ThreadLocal<PNode>(){
			protected PNode initialValue() {
				return new PNode(((TestThread)Thread.currentThread()).getThreadId(),
						((TestThread)Thread.currentThread()).getThreadPriority());
			}
		};
		myPred = new ThreadLocal<PNode>(){
			protected PNode initialValue() {
				return null;
			}
		};
	}

	public void lock() {
		PNode pnode = myNode.get();

		PNode pred = tail.getAndSet(pnode);
		
		if(pred!=null){
			pnode.locked = true;
			lockQueue.add(pnode);
			while(pnode.locked){
				
			}
		}
	}

	public void unlock() {
		PNode me = myNode.get();
		
		PNode head = lockQueue.poll();
		if(head!=null){
			head.locked = false;
		}else
			tail.set(null);
	}
}
