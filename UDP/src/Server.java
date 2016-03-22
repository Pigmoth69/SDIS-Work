import java.io.*; 
import java.net.*;

class Server 
{   
	public static void main(String args[]) throws Exception       
	{     
		if (args.length != 1){
			System.out.println("argumentos errados: Server <port_number>");
			return;
		}
		
		int port_number = Integer.parseInt(args[0]);
		DatagramSocket serverSocket = new DatagramSocket(port_number);       
		byte[] receiveData = new byte[1024];         
		byte[] sendData = new byte[1024];        
		while(true)               
		{                  
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);     
			serverSocket.receive(receivePacket);      
			String sentence = new String( receivePacket.getData(),0,receivePacket.getLength());    
			System.out.println("RECEIVED: " + sentence);      
			
			InetAddress IPAddress = receivePacket.getAddress();     
			int port = receivePacket.getPort();      
			
			String capitalizedSentence = sentence;  
			sendData = capitalizedSentence.getBytes();            
			DatagramPacket sendPacket =           
					new DatagramPacket(sendData, sendData.length, IPAddress, port);    
			serverSocket.send(sendPacket);          
		}    
	} 
}