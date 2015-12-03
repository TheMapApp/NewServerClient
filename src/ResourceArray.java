import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mickelborg on 01-12-2015.
 */
public class ResourceArray {

    //This class will assign an array of resource numbers and types with the length of 20 and 19. The shuffleArray will shuffle the numbers correspondingly so that the ServerProgram will
    //be able to send the grid information of the hexagon to the clients in order to create the resources and numbers.
    public static int[] resourceType = { 1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5 };
    public static int[] resourceNumber = { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};

    public static int[] shuffleArray(int[] ar){
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }
}
