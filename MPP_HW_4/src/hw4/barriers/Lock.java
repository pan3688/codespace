package hw4.barriers;

import java.util.concurrent.atomic.AtomicBoolean;

public class Lock {

	AtomicBoolean state = new AtomicBoolean(false);
	
	public void lock(){
		
		while(true){
			while(state.get());
			if(!state.getAndSet(true))
				return;
		}
	}
	
	public void unlock(){
		state.set(false);
	}
	
}
