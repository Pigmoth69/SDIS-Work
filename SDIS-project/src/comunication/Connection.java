package comunication;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class Connection {
	private MulticastSocket recSocket;
	private DatagramSocket sendSocket;
	private InetAddress mAddr;
	
	private String mcast_addr;
	private int mcast_port;
	
	public Connection(String mcast_addr, int mcast_port) throws IOException{
		this.mcast_addr = mcast_addr;
		this.mcast_port = mcast_port;
		
		mAddr = InetAddress.getByName(mcast_addr);
		recSocket = new MulticastSocket(mcast_port);
		sendSocket = new DatagramSocket();	
		
		recSocket.setTimeToLive(1);
		recSocket.joinGroup(mAddr);
		System.out.println("connected: "+mcast_addr + " port: "+mcast_port);
	}

	public void send(byte[] msg) throws IOException{
		
		DatagramPacket msgPacket = new DatagramPacket(msg,msg.length, mAddr, mcast_port);
		System.out.println("Sent: " + new String(msg).substring(0, 40) + "...");
		sendSocket.send(msgPacket); 
        
	}
	
	public String receive() throws IOException{
		byte[] buf = new byte[70000];
		
		DatagramPacket msgPacket = new DatagramPacket(buf, 0,buf.length);
		recSocket.receive(msgPacket);
        String msg = new String(msgPacket.getData(), 0,msgPacket.getLength() );
        System.out.println("Received: " + msg.substring(0, 40) + "...");
        return msg;
	}
}
