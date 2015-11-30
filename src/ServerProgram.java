/**
 * Created by Mickelborg on 30-11-2015.
 */
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.Date;

public class ServerProgram extends Listener{

    static Server server;

    static int udpPort = 28000, tcpPort = 28000;

    public static void main(String[] args) throws Exception {
        System.out.println("Creating the server!");
        server = new Server();

        server.getKryo().register(PacketMessage.class);

        server.bind(tcpPort, udpPort);

        server.start();

        server.addListener(new ServerProgram());

        System.out.println("System is operational!")
    }


    public void connected(Connection c) {
        System.out.println("Received a connection from"+c.getRemoteAddressTCP().getHostString());
        PacketMessage packetMessage = new PacketMessage();
        packetMessage.message = "Hello mate! The time is: " +new Date().toString();

        c.sendTCP(packetMessage);
    }

    public void received(Connection c, Object p) {

    }

    public void disconnected(Connection c)  {
        System.out.println("A client disconnected");
    }
}
