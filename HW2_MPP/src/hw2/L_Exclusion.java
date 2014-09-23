package hw2;

import hw2.edu.vt.ece.bench.TestThread;
import hw2.edu.vt.ece.locks.Lock;

public class L_Exclusion implements Lock {

	private volatile int[] level;
	private volatile int[] victim;
	private volatile int l;			// this variable will store how many threads are already in CS
	private final int L;			// this variable will store the value of l (small L) i.e. the number of threads allowed in CS

	public L_Exclusion() {
		this(3);
	}

	public L_Exclusion(int n){
		level = new int[n];
		victim = new int[n];
		
		// for mutual exclusion, L should be 1
		L=1;
		l = 0;
		
		for(int i=0; i<n; level[i++]=0);
	}
	
	@Override
	public void lock() {
		int me = ((TestThread)Thread.currentThread()).getThreadId();
		
		/*
		 * number of waiting levels needed will be level.length minus L since L threads can be in CS at the same time
		 */
		for(int i=1; i<=level.length-L; i++){
			level[me] = i;
			victim[i] = me;
			boolean found = false;
			do{
				for(int k=0; k<level.length; k++){
					if(k!=me && (found = (level[k] >= i && victim[i] == me))){
						break;
					}
				}
			}while(found);		// since i runs from 1 till (n-L) ,
								// more than 1 thread coming out from this do-while is a possibility 
								// and hence they can enter CS
			
			while(l>=L);		// if already L threads are in CS, then wait...
		}
		l++;		// one thread going in, hence updating the count of threads going in CS
	}

	@Override
	public void unlock() {
		int me = ((TestThread)Thread.currentThread()).getThreadId();
		level[me] = 0;
		l--;	// one thread coming out, hence reducing the count of threads in CS
	}
}
