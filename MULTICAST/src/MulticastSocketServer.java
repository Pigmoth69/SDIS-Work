import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSocketServer {
    
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

    /**
     * @param args
     * @throws UnknownHostException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
    	if (args.length != 3){
    		System.out.println("erro nos argumentos: <srvc_port> <mcast_addr> <mcast_port> ");
    	}
    	
    	System.out.println("argumentos ok");
        // Get the address that we are going to connect to.
    	int srvc_port = Integer.parseInt(args[0]);
        InetAddress addr = InetAddress.getByName(args[1]);
        int mcast_port = Integer.parseInt(args[2]);
        
        System.out.println("parse ok");
     
        // Open a new DatagramSocket, which will be used to send the data.
        try (MulticastSocket serverSocket = new MulticastSocket(mcast_port)) {
        	serverSocket.joinGroup(addr);
        	
            for (int i = 0; i < 30; i++) {
                String msg = "Sent message no " + i;

                // Create a packet that will contain the data
                // (in the form of bytes) and send it.
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, addr, mcast_port);
                serverSocket.send(msgPacket);
     
                System.out.println("Server sent packet with msg: " + msg);
                Thread.sleep(500);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
