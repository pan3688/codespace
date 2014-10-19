package hw4.prioritylocks;

public class PNode{

	private boolean locked;
	private int priority;
	private int id;
	
	public PNode(int id,int priority) {
		this.locked = false;
		this.priority = priority;
		this.id = id;
	}
	
	public boolean isLocked() {
		return this.locked;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getNodeId() {
		return this.id;
	}
	/*public static void main(String[] args) {
		PNode pnode1 = new PNode(1,5);
		PNode pnode2 = new PNode(2,5);
		PNode pnode3 = new PNode(3,5);
		PNode pnode4 = new PNode(4,3);
		PNode pnode5 = new PNode(5,3);
		
		PriorityBlockingQueue<PNode> pbq = new PriorityBlockingQueue<PNode>(24, new Comparator<PNode>() {
			public int compare(PNode pn1, PNode pn2) {
				if(pn1.getPriority() < pn2.getPriority())
					return -1;
				else if(pn1.getPriority() > pn2.getPriority())
					return 1;
				
				return 0;
			}
		});		
		pbq.add(pnode1);
		pbq.add(pnode2);
		pbq.add(pnode3);
		pbq.add(pnode4);
		pbq.add(pnode5);
		
		for(Object temp : pbq.toArray()){
		//	temp = pbq.remove();
			System.out.println(((PNode)temp).id + "\t" + ((PNode)temp).getPriority());
		}
		//System.out.println("\nROUND 2\n");
		pbq.add(new PNode(6,2));
		pbq.add(new PNode(7,4));
		
		PNode temp;
		while(!pbq.isEmpty()){
				temp = pbq.remove();
				System.out.println(((PNode)temp).id + "\t" + ((PNode)temp).getPriority());
		}
		
	}*/
}
