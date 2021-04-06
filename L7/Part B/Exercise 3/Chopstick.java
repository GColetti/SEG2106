//import java.util.concurrent.Semaphore;

public class Chopstick {

	
	
	
	private int ID;
	private boolean chopstickFree;
	private int waitTime = 2000;
	
	Chopstick(int ID) {
		this.ID = ID
		;
		chopstickFree = true;
	}
	
	synchronized boolean take() {
		if (!chopstickFree) {
			try {
				wait(waitTime);
			} catch (InterruptedException e) {}
		}
		if (chopstickFree) { //Chopstick is free
			chopstickFree = false;
			notifyAll();
			return true;
		} else {
			return false;
		}
	}
	
	synchronized void release() {
		if (!chopstickFree) { //Chopstick is in use
			chopstickFree = true;
			
			notify();
		}
	}
	    
	public int getID() {
	    return(ID);
	}
}
