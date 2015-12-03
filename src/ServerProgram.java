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

        if(players == 3){
            Turn tur = new Turn();
            tur.turn = 1;
            server.sendToTCP(1, tur);
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
        }
    }

    public void disconnected(Connection c)  {
        System.out.println("A client disconnected");
    }
}