import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
	       
		    int n = 0; 
	        try (Scanner s=new Scanner(System.in)) {
	        	while (true){
	        		System.out.println("Enter the number of Queens :");
	        		n = s.nextInt();
	        		if ( n == 2 || n ==3) {
	        			System.out.println("No Solution possible for "+ n +" Queens. Please enter another number");
	        		}
	        		else{
	        			break;
					}
	        	}
	        }
	        long timestamp1 = System.currentTimeMillis();
	        
	        System.out.println("Solution to "+ n +" queens using hill climbing search:");
	        
			// Instantiate thread group
			ThreadGroup thrdgroup = new ThreadGroup("HCS");
 
			// Create mutiple threads that run HCS and all look for solution
			HillClimbingSearch hcs;
			for (int i = 0; i<n; i++){
				hcs = new HillClimbingSearch(n);
				hcs.run();

				if (hcs.getFinalSolution() != null) {
	        		hcs.printState(hcs.getFinalSolution());

					//Stop all running threads and exit loop
					thrdgroup.interrupt();
					break;
				}
			}

	        //Printing the solution
	        long timestamp2 = System.currentTimeMillis();
			
			long timeDiff = timestamp2 - timestamp1;
			System.out.println("Execution Time: "+timeDiff+" ms");
	        
	       
	    }
}