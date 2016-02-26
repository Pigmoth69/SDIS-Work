package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	public static void main(String[] args) throws IOException{
		
		if (args.length != 4 && args.length !=5) {
			System.out.println("Usage: java Client <host_name> <port_number> <oper> <opnd>*");
			/*
			  <host_name> is the name of the host running the server;
			  <port_number> is the server port;
			  <oper> is either ‘‘register’’or ‘‘lookup’’
			  <opnd>* is the list of arguments
				<plate number> <owner name>, for register;
				<plate number>, for lookup.
			 */
			return;
		}
		
		DatagramSocket socket = new DatagramSocket();

		//prepara o envio da mensagem
		String request;
		if(args[2].equals("REGISTER"))
			request = args[2]+" "+args[3]+" "+args[4];
		else if(args[2].equals("LOOKUP"))
			request = args[2]+" "+args[3];
		else{
			System.out.println("Deu erro!");
			return;
		}
		System.out.println("REQUEST: "+ request);
		
		//envia
		byte[] sbuf = request.getBytes();
		InetAddress address = InetAddress.getByName(args[0]);
		int portNumber = Integer.parseInt(args[1]);
		DatagramPacket packet = new DatagramPacket(sbuf, sbuf.length,address, portNumber);
		socket.send(packet);
		System.out.println("enviou!");
		//recebe
		byte[] rbuf = new byte[sbuf.length];
		packet = new DatagramPacket(rbuf, rbuf.length);
		System.out.println("vou receber!");
		socket.receive(packet);
		System.out.println("recebi!!");
		String received = new String(packet.getData());
		System.out.println("Resposta: "+received);
		socket.close();
	}
}
