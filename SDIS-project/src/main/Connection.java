package main;
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

	public void send(String msg) throws IOException{
		DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),msg.getBytes().length, mAddr, mcast_port);
		sendSocket.send(msgPacket); 
       // System.out.println("Server sent packet with msg: " + msg);
	}
	
	public String receive() throws IOException{
		byte[] buf = new byte[70000];
		
		DatagramPacket msgPacket = new DatagramPacket(buf, 0,buf.length);
		recSocket.receive(msgPacket);
        String msg = new String(msgPacket.getData(), 0,msgPacket.getLength() );
        return msg;
	}
}
