package hw2;

import hw2.edu.vt.ece.bench.Counter;
import hw2.edu.vt.ece.bench.SharedCounter;
import hw2.edu.vt.ece.bench.TestThread;
import hw2.edu.vt.ece.locks.*;

/**
 * 
 * @author Mohamed M. Saad
 */
public class Test {

	/*
	 * Change Thread_Count to 16 for testing TreeLock algorithm
	 * Change back Thread_Count to 2 for testing the rest
	 */
	private static final int THREAD_COUNT = 2;
	
	private static final String LOCK_ONE = "LockOne";
	private static final String LOCK_TWO = "LockTwo";
	private static final String PETERSON = "Peterson";
	private static final String FILTER = "Filter";
	private static final String TREE_LOCK = "TreeLock";
	private static final String L_Exclusion = "L_Exclusion";

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String lockClass = (args.length==0 ? PETERSON : args[0]);
		final Counter counter = new SharedCounter(0, (Lock)Class.forName("hw2." + lockClass).newInstance());
		for(int t=0; t<THREAD_COUNT; t++)
			new TestThread(counter).start();
	}
}
