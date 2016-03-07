package MULTICAST_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastServer {
    
    static String MCAST_ADDR; //224.0.0.3
    static int SRVC_PORT; 	 //8888
    static int MCAST_PORT;

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
    	if(args.length != 3){
    		System.out.println("Invalid Number of Arguments!");
    		System.out.println(args);
    		return;
    	}
    	SRVC_PORT = Integer.parseInt(args[0]);
    	MCAST_ADDR = args[1];
    	MCAST_PORT = Integer.parseInt(args[2]);
    	
    	
		try (MulticastSocket socket = new MulticastSocket(MCAST_PORT)){
			//Get the address that we are going to connect to.
			InetAddress group = InetAddress.getByName(MCAST_ADDR);
			socket.joinGroup(group);
			
			for (int i = 0; i < 5; i++) {
                String msg = "Sent message no " + i;
                
                // Create a packet that will contain the data
                // (in the form of bytes) and send it.
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),msg.getBytes().length);
                socket.send(msgPacket); 
			
                System.out.println("Server sent packet with msg: " + msg);
                Thread.sleep(500);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}
/*
java Server <srvc_port> <mcast_addr> <mcast_port> 
where:
<srvc_port> is the port number where the server provides the service
<mcast_addr> is the IP address of the multicast group used by the server to advertise its service.
<mcast_port> is the multicast group port number used by the server to advertise its service.
*/