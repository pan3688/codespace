package hw4.prioritylocks;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import hw4.locks.Lock;

public class PriorityCLH implements Lock{

	private final int initialCapacity = 96;
	
	private PriorityBlockingQueue<PNode> lockQueue;
	private AtomicReference<PNode> tail;
	ThreadLocal<PNode> myNode;
	
	public PriorityCLH() {
		
		tail = new AtomicReference<PNode>(null);
		
		lockQueue = new PriorityBlockingQueue<PNode>(initialCapacity,new Comparator<PNode>() {
			public int compare(PNode pn1, PNode pn2) {
				if(pn1.getPriority() < pn2.getPriority())
					return -1;
				else if(pn1.getPriority() > pn2.getPriority())
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
	}
	
	
	@Override
	public void lock() {
		PNode pnode = myNode.get();
		pnode.setLocked(true);
		
		PNode pred = tail.getAndSet(pnode);
		
		if(pred != null){
			lockQueue.add(pnode);
			while(pnode.isLocked());
		}
	}

	@Override
	public void unlock() {
		PNode me = myNode.get();
		me.setLocked(false);
		
		while(lockQueue.peek() == null);
		
		PNode head = lockQueue.poll();
		head.setLocked(false);
	}
}
