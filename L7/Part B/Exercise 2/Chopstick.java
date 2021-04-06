import java.util.concurrent.Semaphore;

public class Chopstick {
	
	private int ID;
	private boolean chopstickFree;
	Semaphore sem = new Semaphore(4);
	
	Chopstick(int ID) {
		this.ID = ID;
		chopstickFree = true;
	}
	
	synchronized void take() throws InterruptedException {
		if (chopstickFree) { //Chopstick is free
			chopstickFree = false;
			sem.acquire();
			
		} else {
			//Wait until chopstick becomes free
			while (!chopstickFree)  {
				wait();
			}
		}
	}
	
	synchronized void release() {
		if (!chopstickFree) { //Chopstick is in use
			chopstickFree = true;
			sem.release();
			notifyAll();
		}
	}
	    
	public int getID() {
	    return(ID);
	}
}
