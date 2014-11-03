package hw4.locks;

public class QNode {
	volatile boolean locked;
	volatile QNode next;
}
