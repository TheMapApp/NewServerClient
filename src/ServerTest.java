import javax.swing.JFrame;
public class ServerTest {
	public static void main(String[] args){
		Server sally = new Server();
		sally.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sally.startRunning();
		
		Resource ressy = new Resource();
		
		shuffleArray(resourceType);
	    for (int j = 0; j < resourceType.length; j++) {
	      System.out.print(resourceType[j] + " ");
	    }
	    System.out.println();
	 }
}
