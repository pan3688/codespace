package hw2.treelock;

import hw2.edu.vt.ece.bench.TestThread;
import hw2.edu.vt.ece.locks.Lock;
import hw2.edu.vt.ece.locks.Peterson;
import hw2.edu.vt.ece.util.Tree;

public class TreeLock implements Lock {

	private Tree<Integer> tree;
	private Lock[] treeLock;
	
	public TreeLock(Integer root) {
		tree = new Tree<Integer>(root);
		
		tree.add(4);
		tree.add(2);
		tree.add(6);
		tree.add(1);
		tree.add(3);
		tree.add(5);
		tree.add(7);
		
		tree.add(12);
		tree.add(10);
		tree.add(14);
		tree.add(9);
		tree.add(11);
		tree.add(13);
		tree.add(15);
		
		treeLock = new Peterson[15];
		for(int i=0; i < 15; i ++)
			treeLock[i] = new Peterson();
		
		
	}
	
	public TreeLock() {
		this(8);
	}

	@Override
	public void lock() {
		// TODO Auto-generated method stub
		int i = ((TestThread)Thread.currentThread()).getThreadId();
		
		int[] threadPath = getPath(i);
		
		for(int j : threadPath){
			
			
		}
		
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub

	}
	
	private int[] getPath(int threadID){
		
		int[] path = new int[4];
		int i = 3;
		
		Tree<Integer> itr = tree;
		
		path[i] = (Integer)itr.getData();
		while(itr.getLeftChild() !=null || itr.getRightChild() != null){
			if(threadID < path[i] && itr.getLeftChild() !=null)
				itr = itr.getLeftChild();
			else if(itr.getRightChild() != null)
				itr = itr.getRightChild();
			
			path[--i] = (Integer)itr.getData();
		}
		
		return path;
	}
	
	/*
	 * Method for testing the B-Tree
	 */
	public static void printLeftRight(Tree node){
		
		if(node.getLeftChild() !=null || node.getRightChild() != null){
			System.out.println("Node\t" + node.getData() + 
					"\tLeft\t" + node.getLeftChild().getData() + "\tRight\t" + node.getRightChild().getData());
			
			printLeftRight(node.getLeftChild());;
			printLeftRight(node.getRightChild());;
			
		}
	}
	
	public static void main(String[] args) {
		/*Tree<Integer> tree = new TreeLock(8).tree;
		
		Tree<Integer> itr = tree;
		
		do{
			System.out.println(itr.getData());
		}while((itr = itr.getLeftChild())!=null);
		// print right branch from the root till the right-most leaf
		itr = tree;
		do{
			System.out.println(itr.getData());
		}while((itr = itr.getRightChild())!=null);*/
//		printLeftRight(treeLock.tree);
		
		TreeLock treeLock = new TreeLock();
		for(int i = 0;i<16;i++){
			System.out.print(i);
			for(int j : treeLock.getPath(i))
				System.out.print("\t" +j);
			
			System.out.println("");
		}
	}
	
	
}
