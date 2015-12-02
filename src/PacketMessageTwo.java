import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mickelborg on 01-12-2015.
 */
public class PacketMessageTwo {

    //public int[] massage;
    public static int[] resourceType = { 1,1,1,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5 };

    public static int[] shuffleArray(int[] ar){
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
        return ar;
    }
}
