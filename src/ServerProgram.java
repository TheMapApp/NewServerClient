/**
 * Created by Mickelborg on 30-11-2015.
 */
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.Arrays;
import java.util.Date;

public class ServerProgram extends Listener{

    public static String temp;
    public static int[] temptwo = new int[18];

    public int[] resourceNumber = {2,3};

    static Server server;

    int colorCode = 0;

    static int udpPort = 28000, tcpPort = 28000;

    public static void main(String[] args) throws Exception {
        System.out.println("Creating the server!");
        server = new Server();

        server.getKryo().register(PacketMessage.class);
        server.getKryo().register(PacketMessageTwo.class);
        server.getKryo().register(PacketMessageTwo[].class);

        server.bind(tcpPort, udpPort);

        server.start();

        server.addListener(new ServerProgram());

        System.out.println("Server is operational!");
    }

    public void connected(Connection c) {
        System.out.println("Received a connection from"+c.getRemoteAddressTCP().getHostString());
        PacketMessage packetMessage = new PacketMessage();
        PacketMessage colour = new PacketMessage();

        PacketMessageTwo.shuffleArray(PacketMessageTwo.resourceType);

        packetMessage.message = "Hello mate! The time is: " +new Date().toString();

        c.sendTCP(packetMessage);
        c.sendTCP(Arrays.toString(PacketMessageTwo.resourceType));

        String sendTest = new String();
        sendTest = Arrays.toString(PacketMessageTwo.resourceType);
        PacketMessage arraySend = new PacketMessage();
        arraySend.message = sendTest;
        c.sendTCP(arraySend);

        for (int i = 0; i <= c.getID(); i++) {
            if (colorCode == 0) {
                c.getID();
                System.out.println("Blue is applied to " + c.getID());
                colour.message = "Hi there, blue!";
                c.sendTCP(colour);
                colorCode += 1;
            } else if (colorCode == 1) {
                c.getID();
                System.out.println("Green is applied to " + c.getID());
                colour.message = "Hi there, green!";
                c.sendTCP(colour);
                colorCode += 1;
            } else if (colorCode == 2) {
                c.getID();
                System.out.println("Red is applied to " + c.getID());
                colour.message = "Hi there, red!";
                c.sendTCP(colour);
                colorCode += 1;
            } else if (colorCode == 3) {
                c.getID();
                System.out.println("Black is applied to " + c.getID());
                colour.message = "Hi there, black!";
                c.sendTCP(colour);
                colorCode += 1;
            }
        }
    }

    public void received(Connection c, Object p) {
    }

    public void disconnected(Connection c)  {
        System.out.println("A client disconnected");
    }
}