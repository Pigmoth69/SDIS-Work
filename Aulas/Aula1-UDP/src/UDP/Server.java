package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Server {
	HashMap<String,String> database = new HashMap<String,String>();
	
	public static void main(String[] args) throws IOException{
		if (args.length != 1) {
			System.out.println("Usage: java Server <port_number>");
			return;
		}
		int portNumber = Integer.parseInt(args[0]);
		
		DatagramSocket socket = new DatagramSocket(portNumber);
		String received;
		
		while(true){
			//recebe
			byte[] rbuf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(rbuf, rbuf.length);
			System.out.println("SERVER: Receiving..");
			socket.receive(packet);
			received = new String(packet.getData());
			
			System.out.println("RECEBIDO: "+ received);
			received = null;
			
			//envia
			/*byte[] sbuf = args[1].getBytes();
			InetAddress address = InetAddress.getByName(args[0]);
			DatagramPacket packet = new DatagramPacket(sbuf, sbuf.length,address, portNumber);
			socket.send(packet);*/
		}

		//socket.close();
	}

	public String findPlate(int plate_number){
		/*database.put("matricula", "Dono");*/
		/* Display content using Iterator*/
		Set set = database.entrySet();
		Iterator iterator = set.iterator();
		
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			
			System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
			System.out.println(mentry.getValue());
		}
		return null;

	}
}



