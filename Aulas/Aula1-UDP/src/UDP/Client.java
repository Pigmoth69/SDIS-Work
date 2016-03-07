package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				host 4445 REGISTER 123 daniel
			 */
			return;
		}
		DatagramSocket socket;
		
		int portNumber = Integer.parseInt(args[1]);
		InetAddress address = InetAddress.getByName(args[0]);
		socket = new DatagramSocket(portNumber,address);

		//prepara o envio da mensagem
		String request;
		if(args[2].equals("REGISTER")){
				request = args[2]+" "+args[3]+" "+args[4];
		}	
		else if(args[2].equals("LOOKUP"))
			request = args[2]+" "+args[3];
		else{
			System.out.println("Deu erro!");
			return;
		}
		System.out.println("REQUEST: "+ request);
		
		//envia
		byte[] sbuf = request.getBytes();
		DatagramPacket packet = new DatagramPacket(sbuf, sbuf.length,address, portNumber);
		socket.send(packet);
		System.out.println("enviou!");

		
		//recebe
		byte[] rbuf = new byte[sbuf.length];
		packet = new DatagramPacket(rbuf, rbuf.length);
		System.out.println("vou receber!");
		//espera para receber o feedback do servidor!
		socket.receive(packet);
		System.out.println("recebi!!");
		String received = new String(packet.getData());
		System.out.println("Resposta: "+received);
		socket.close();
	}
}
