/**
 * Created by Mickelborg on 30-11-2015.
 */
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.util.Arrays;
import java.util.Date;

public class ServerProgram extends Listener{
    public boolean firstJoin = true;
    public int players = 0;
    static Server server;

    static int udpPort = 54555, tcpPort = 54555;

    public int turn = 1;

    public static void main(String[] args) throws Exception {
        System.out.println("Creating the server!");
        server = new Server();

        server.getKryo().register(HousePosX.class);
        server.getKryo().register(HousePosY.class);
        server.getKryo().register(RoadX1.class);
        server.getKryo().register(RoadX2.class);
        server.getKryo().register(RoadY1.class);
        server.getKryo().register(RoadY2.class);
        server.getKryo().register(PlayerColor.class);

        //server.getKryo().register(PacketMessage.class);
        server.getKryo().register(int[].class);
        server.getKryo().register(Ressources.class);
        server.getKryo().register(ResType.class);
        server.getKryo().register(Turn.class);
        server.getKryo().register(DiceRoll.class);

        server.getKryo().register(TownX.class);
        server.getKryo().register(TownY.class);


        server.bind(tcpPort, udpPort);

        server.start();

        server.addListener(new ServerProgram());

        System.out.println("Server is operational!");
    }

    public void connected(Connection c) {
        System.out.println("Received a connection from"+c.getRemoteAddressTCP().getHostString());
        players += 1;
        System.out.println(players);

        Log.set(Log.LEVEL_DEBUG);
        Ressources res = new Ressources();
        ResType resType = new ResType();
        if(firstJoin){
            ResourceArray.shuffleArray(ResourceArray.resourceNumber);
            ResourceArray.shuffleArray(ResourceArray.resourceType);
            firstJoin = false;
        }
        res.res = ResourceArray.resourceNumber;
        resType.resType = ResourceArray.resourceType;


        server.sendToTCP(c.getID(), res);
        server.sendToTCP(c.getID(), resType);
        //PLAYER COLOR
        PlayerColor playerColor = new PlayerColor();
        playerColor.playerColor = c.getID();
        server.sendToTCP(c.getID(), playerColor);

        if(players == 2){
            Turn tur = new Turn();
            tur.turn = 1;
            server.sendToTCP(1, tur);
            DiceRoll roll = new DiceRoll();
            roll.dieRoll = Roll();
            server.sendToAllTCP(roll);
        }
        //c.sendTCP(Arrays.toString(ResourceArray.resourceNumber));
        /*
        String sendType;
        sendType = Arrays.toString(ResourceArray.resourceType);
        PacketMessage arraySend = new PacketMessage();
        arraySend.message = sendType;
        c.sendTCP(arraySend);

        String sendNumber;
        sendNumber = Arrays.toString(ResourceArray.resourceNumber);
        PacketMessage arraySend2 = new PacketMessage();
        arraySend2.message = sendNumber;
        c.sendTCP(arraySend2);
        */
    }

    public void received(Connection c, Object p) {
        if (p instanceof HousePosX) {
            HousePosX var = new HousePosX();
            var.x = c.getID();
            System.out.println("Receieved x");
            server.sendToAllExceptTCP(c.getID(), p);
        }
        if (p instanceof HousePosY) {
            System.out.println("Receieved y");
            server.sendToAllExceptTCP(c.getID(), p);
        }
        if (p instanceof RoadX1) {
            System.out.println("Receieved y");
            server.sendToAllExceptTCP(c.getID(), p);
        }
        if (p instanceof RoadX2) {
            System.out.println("Receieved y");
            server.sendToAllExceptTCP(c.getID(), p);
        }
        if (p instanceof RoadY1) {
            System.out.println("Receieved y");
            server.sendToAllExceptTCP(c.getID(), p);
        }
        if (p instanceof RoadY2) {
            System.out.println("Receieved y");
            server.sendToAllExceptTCP(c.getID(), p);
        }
        if(p instanceof Turn){
            Turn tur = new Turn();
            turn += 1;
            if(turn > players){
                turn = 1;
            }
            tur.turn = turn;
            server.sendToAllTCP(tur);
            DiceRoll roll = new DiceRoll();
            roll.dieRoll = Roll();
            server.sendToAllTCP(roll);
        }
        if (p instanceof TownX) {
            System.out.println("Receieved TownX");
            server.sendToAllExceptTCP(c.getID(), p);
        }
        if (p instanceof TownY) {
            System.out.println("Receieved TownY");
            server.sendToAllExceptTCP(c.getID(), p);
        }
    }

    public void disconnected(Connection c)  {
        System.out.println("A client disconnected");
    }

    public int Roll(){ //method needed to roll the dice in the game

		/*in this method the dice will be rolled
		 * So the point is to create a method that allows for two dice to be rolled
		 * Each die gets a number from 1 to 6. These values have to be random.
		 * Afterwards, these two values will be added together, creating a sum.
		 * The sum will be the result of the dice per roll.
		 * Two dice with values from 1 to 6 are used in order to be sure that once added, 7 will be the most common number (a function of the game)
		*/

        int die1, die2; //two dice variables are created

		/*generating a random number:
		 * randomNum = minimum + (int)(Math.random()*maximum);
		 * Here, the minimum is 1 and the maximum is 6
		 */

        die1 = 1 + (int)(Math.random()*6);
        die2 = 1 + (int)(Math.random()*6);

        int sum = die1 + die2;
        //adding the random value from die1 to the random value of die2
        //integers are used because dice are whole numbers
        //the sum can be any number between 2 and 12

        System.out.println(sum);

        return sum;


    }
}
