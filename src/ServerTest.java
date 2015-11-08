import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ServerTest {
	public static int[] resourceType = {1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5};	
	public static int[] resourceNumber = {2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12};
	
	//public int[] playerColor = new int[4];

	public static void main(String[] args){
		Server sally = new Server();
		sally.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sally.startRunning();
		
		shuffleArray(resourceType);
		for (int i = 0; i < resourceType.length; i++) {
		  System.out.print(resourceType[i] + " ");
		}
		System.out.println();
		}

	public static void shuffleArray(int[] resourceType) {
		 Random rnd = ThreadLocalRandom.current();
		    for (int i = resourceType.length - 1; i > 0; i--)
		    {
		    	
		      int index = rnd.nextInt(i + 1);
		      // Simple swap
		      int a = resourceType[index];
		      resourceType[index] = resourceType[i];
		      resourceType[i] = a;
		    }
		    
		//Ressource ressy = new Ressource();
		
		
		shuffleArray(resourceType);
	    for (int j = 0; j < resourceType.length; j++) {
	      System.out.print(resourceType[j] + " ");
	    }
	    System.out.println();
	
	    shuffleArray(resourceNumber);
    for (int j = 0; j < resourceNumber.length; j++) {
      System.out.print(resourceNumber[j] + " ");
    }
    System.out.println();
	
		}
	}



