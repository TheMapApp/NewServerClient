import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Resource{
public int[] resourceType = new int[18];
public int[] resourceNumber = new int[18];
public int[] playerColor = new int[4];
public int Stone;
public int Clay;
public int Wheat;
public int Wood;
public int Sheep;
	
	/*public void mapSetup(){
		//int[] numbers = {2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};
			
			/*if(rando == 0 && Stone<3){
				Stone+=1;
			}
			else if(rando == 1 && Clay<3){
				Clay+=1;
			}
			else if(rando == 2 && Wheat<4){
				Wheat+=1;
			}
			else if(rando == 3 && Wood<4){
				Wood+=1;
			}
			else if(rando == 4 && Sheep<4){
				Sheep+=1;
			}
		}
		*/
	
	public static void shuffleArray(int[] ar){
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	    	
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    	}
	  }
}
