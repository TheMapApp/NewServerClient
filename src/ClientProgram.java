import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sun.xml.internal.ws.api.message.Packet;

/**
 * Created by Mickelborg on 30-11-2015.
 */
public class ClientProgram extends Listener {

    static Client client;
    static String ip = "192.168.137.126";
    static int tcpPort = 28000;
    static int udpPort = 28000;

    static boolean messageReceived = false;

    public static void main(String[] args) throws Exception  {

        System.out.println("Connecting to the server!");

        client = new Client();

        client.getKryo().register(PacketMessage.class);

        client.start();

        client.connect(5000, ip, tcpPort, udpPort);

        client.addListener(new ClientProgram());

        System.out.println("Connected! The client program is now waiting for a packet.");

        while(!messageReceived) {
            Thread.sleep(10000000);
        }
        //System.out.println("Client will now exit!");
        //System.exit(0);
    }

    public void received(Connection c, Object p)    {
        if(p instanceof PacketMessage)  {
            PacketMessage packet = (PacketMessage) p;
            System.out.println("Received a message from the host: "+packet.message);
            messageReceived = true;
        }
    }
}
