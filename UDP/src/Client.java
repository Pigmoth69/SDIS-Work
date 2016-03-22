import java.io.*; 
import java.net.*; 

class Client 
{    
	public static void main(String args[]) throws Exception    
	{       
		if (args.length != 4 && args.length != 5){
			System.out.println("not enough params <host_name> <port_number> <oper> <opnd>*");
		}
		
		InetAddress host_name = InetAddress.getByName(args[0]);  
		int port_number = Integer.parseInt(args[1]);
		String oper = args[2];
		
		DatagramSocket clientSocket = new DatagramSocket(); 
		
		byte[] sendData;
		byte[] receiveData = new byte[1024];
		String data;
		
		switch(oper){
			case "REGISTER":
				data = args[2] + " " + args[3] + " " + args[4];
				break;
			case "LOOKUP":
				data = args[2] + " " + args[3];
				break;
			default:
				System.out.println("Peido!!!!");
				return;
		}
		
		sendData = data.getBytes();    
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, host_name, port_number); 
		clientSocket.send(sendPacket);
		System.out.println("send");
		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);  
		String modifiedSentence = new String(receivePacket.getData(),0,receivePacket.getLength());  
		System.out.println("FROM SERVER:" + modifiedSentence); 
		clientSocket.close(); 
	}
}
