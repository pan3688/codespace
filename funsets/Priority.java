package hw4.prioritylocks;

/*
 * NOT USED
 */

public enum Priority /*implements Comparator<Priority>*/ {
	ONE(1),TWO(2),THREE(3),FOUR(4),FIVE(5);
	
	private final int priorityValue;
	
	private Priority(final int priorityValue) {
		this.priorityValue = priorityValue;
	}
	
	public int getPriorityValue() {
		return priorityValue;
	}

	/*public int compare(Priority o1, Priority o2) {
		if(o1.getPriority() < o2.getPriority())
			return 1;
		else if(o1.getPriority() > o2.getPriority())
			return -1;
		
		return 0;
	}*/
	
	/*public static void main(String[] args) {
		System.out.println(ONE.compare(ONE, FIVE));
	}*/
}
