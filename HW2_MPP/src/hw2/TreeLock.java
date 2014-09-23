package hw2;

import java.util.HashMap;
import java.util.Map;

import hw2.edu.vt.ece.bench.TestThread;
import hw2.edu.vt.ece.locks.Lock;
import hw2.edu.vt.ece.locks.Peterson;
import hw2.edu.vt.ece.util.Tree;

public class TreeLock implements Lock {

	/*
	 * The tree structure of lock nodes,for the 16 thread lock Mechanism is as below
	 * it will have 15 Peterson locks in total
	 * 
	 * 										8
	 * 
	 * 						4								12
	 * 
	 * 				2				6				10				14
	 * 
	 * 			1		3		5		7		9		11		13		15
	 * 
	 * scope for improvement -- make the tree structure dynamic
	 */
	private Tree<Integer> tree;
	
	/*
	 * The tree structure in tree attribute will hold the nodes,
	 * where each node is a Peterson Lock
	 */
	private Lock[] treeLock;
	
	/*
	 * For faster retrieval of each leaf nodes' path from the root 
	 */
	private Map<Integer, int[]> pathMap;
	
	public TreeLock() {
		
		/*
		 * Creating the binary tree for lock nodes
		 */
		tree = new Tree<Integer>(8);
		
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
		
		/*
		 * array of lock nodes, including leaf and non-leaf elements 
		 */
		treeLock = new Peterson[15];
		for(int i=0; i < 15; i ++)
			treeLock[i] = new Peterson();
		
		pathMap = new HashMap<Integer,int[]>();
		
		/*
		 * Since non-leaf nodes will have fixed path from root,
		 * store the path array during initialization
		 */
		for(int i=0;i<16;i++)
			pathMap.put(i, getPath(i));
		
	}

	public void lock() {
		int id = ((TestThread)Thread.currentThread()).getThreadId();
		
		int[] threadPath = (pathMap.get(id)==null	?	getPath(id):	pathMap.get(id));
		
		for(int j=0;j<threadPath.length;j++){
			
			switch(j){
			
				/*
				 * for leaf node, the id will be id%2
				 */
				case 0:	((Peterson)treeLock[threadPath[j] - 1]).lock(id%2);break;
				
				/*
				 * for non-leaf nodes, the id will be (id/nodeID)
				 * for example for the root, all threads with id > 8 , will have id=1 (Integer division)
				 * 9/8 =1 , 10/8 =1 and so on for all non-leaf nodes in the lower levels
				 */
				default: ((Peterson)treeLock[threadPath[j] - 1]).lock(id/threadPath[j]);break;
			}
		}
	}

	public void unlock() {
		int id = ((TestThread)Thread.currentThread()).getThreadId();
		
		int[] threadPath = (pathMap.get(id)==null	?	getPath(id):	pathMap.get(id));
		
		for(int j=threadPath.length-1;j>=0;j--){
			
			switch(j){
				
				case 0:	((Peterson)treeLock[threadPath[j] - 1]).unlock(id%2);break;
				
				default: ((Peterson)treeLock[threadPath[j] - 1]).unlock(id/threadPath[j]);break;
			}
		}
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
		pathMap.put(threadID, path);
		
		return path;
	}
	
	/*
	 * Method for testing the B-Tree
	 * prints all the nodes and their child nodes
	 */
	public static void printLeftRight(Tree<Integer> node){
		
		if(node.getLeftChild() !=null || node.getRightChild() != null){
			System.out.println("Node\t" + node.getData() + 
					"\tLeft\t" + node.getLeftChild().getData() + "\tRight\t" + node.getRightChild().getData());
			
			printLeftRight(node.getLeftChild());;
			printLeftRight(node.getRightChild());;
		}
	}
	
	/*
	 * Method for testing the utility methods
	 */
	public static void main(String[] args) {
		
		TreeLock treeLock = new TreeLock();
	//	for(int i = 0;i<16;i++){
	//		System.out.print(i);
			for(int j : treeLock.getPath(10))
				System.out.print("\t" +j);
			
	//		System.out.println("");
	//	}
	}
	
	
}
