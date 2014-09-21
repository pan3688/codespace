package hw2.edu.vt.ece.locks;

import hw2.edu.vt.ece.bench.TestThread;

public class Filter implements Lock{
	private volatile int[] level;
	private volatile int[] victim;

	public Filter() {
		this(2);
	}

	public Filter(int n){
		level = new int[n];
		victim = new int[n];
		for(int i=0; i<n; level[i++]=0);
	}
	
	@Override
	public void lock() {
		int me = ((TestThread)Thread.currentThread()).getThreadId();
		for(int i=1; i<level.length; i++){
			level[me] = i;
			victim[i] = me;
			boolean found = false;
			do{
				for(int k=0; k<level.length; k++)
					if(k!=me && (found = (level[k] >= i && victim[i] == me)))
						break;
			}while(found);
		}
	}

	@Override
	public void unlock() {
		int me = ((TestThread)Thread.currentThread()).getThreadId();
		level[me] = 0;
	}

}
