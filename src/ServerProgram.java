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

    //Setting the UDP port and TCP port for client connection.
    static int udpPort = 54555, tcpPort = 54555;

    //This turn-integer will act in correspondance with the c.getID(). Whenever the turn is one, the corresponding client number of c.getID() (the first one to join) will have the first turn in the game.
    public int turn = 1;

    public static void main(String[] args) throws Exception {
        System.out.println("Creating the server!");
        //Creating a new server
        server = new Server();

        //Registering all the server components and classes that will be send to the client, e.g. the house position, road position, playercolor, resource types and numbers as well as turn.
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


        //Binding the server to a specific TCP and UDP port, which is initialized at the top (static int udpPort = 54555, tcpPort = 54555)
        server.bind(tcpPort, udpPort);

        //Starting the server
        server.start();

        //Adding the server listener in order to execute certain lines of code when interacting with the client. It acts as a kind of observation tool whenever there is a "transaction between the server and the client.
        server.addListener(new ServerProgram());

        //A small println in order to debug if the code runs this far to create the server.
        System.out.println("Server is operational!");
    }

    //This void contains all the actions that the server will do whenever a client connects to the servers IP. It initially sends a confirmation message
    public void connected(Connection c) {
        System.out.println("Received a connection from"+c.getRemoteAddressTCP().getHostString());
        //players += 1; will add the number of clients connected in order to store an overview of the amount of players.
        players += 1;
        System.out.println(players);

        //This log was initially used to debug why the resourceNumber weren't send, although the resourceNumber was sent
        Log.set(Log.LEVEL_DEBUG);
        //Creating the new resources and resource-types in order to send them to the client.
        Ressources res = new Ressources();
        ResType resType = new ResType();
        //When the first player joins, the resource numbers and types will be shuffled in order to create a random map and distribute the numbers randomly.
        if(firstJoin){
            ResourceArray.shuffleArray(ResourceArray.resourceNumber);
            ResourceArray.shuffleArray(ResourceArray.resourceType);
            firstJoin = false;
        }
        //Preparing the packets in order to be sent
        res.res = ResourceArray.resourceNumber;
        resType.resType = ResourceArray.resourceType;


        //Sending the packets containing the resource number and the resource type to the client
        server.sendToTCP(c.getID(), res);
        server.sendToTCP(c.getID(), resType);
        //Creating playercolors based on the c.getID(), so that they will have different colors for each player.
        PlayerColor playerColor = new PlayerColor();
        playerColor.playerColor = c.getID();
        //Sending/assigning the colour to the player.
        server.sendToTCP(c.getID(), playerColor);

        //This will start the game if there is a specific amount of players, in this case #. Afterwards, in the Turn tur = new Turn();, the server will give the first turn to the first player/client that connected to the server.
        if(players == 2){
            Turn tur = new Turn();
            tur.turn = 1;
            server.sendToTCP(1, tur);
            DiceRoll roll = new DiceRoll();
            roll.dieRoll = Roll();
            server.sendToAllTCP(roll);
        }
    }

    //The following void received will send the following positions of the houses, roads and the turn as well to the corresponding other clients.
    public void received(Connection c, Object p) { // A function that gets called when something is received
        if (p instanceof HousePosX) { //If it is the x position for the house
            HousePosX var = new HousePosX();
            var.x = c.getID();
            System.out.println("Receieved x");
            server.sendToAllExceptTCP(c.getID(), p); // Send that position to all clients
        }
        if (p instanceof HousePosY) { //If it is the y position for the house
            System.out.println("Receieved y");
            server.sendToAllExceptTCP(c.getID(), p); // Send that position to all clients
        }
        if (p instanceof RoadX1) { //If it is the first x position for the of the road
            System.out.println("Receieved y");
            server.sendToAllExceptTCP(c.getID(), p); // Send that position to all clients
        }
        if (p instanceof RoadX2) { //If it is the second x position for the of the road
            System.out.println("Receieved y");
            server.sendToAllExceptTCP(c.getID(), p); // Send that position to all clients
        }
        if (p instanceof RoadY1) { //If it is the first y position for the of the road
            System.out.println("Receieved y");
            server.sendToAllExceptTCP(c.getID(), p); // Send that position to all clients
        }
        if (p instanceof RoadY2) { //If it is the second x position for the of the road
            System.out.println("Receieved y");
            server.sendToAllExceptTCP(c.getID(), p); // Send that position to all clients
        }
        //This if-statement assigns the next players/clients turn.
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

    //This void will print the corresponding line of code if a client loses connection or disconnects.
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