package test;

import java.io.IOException;

import main.Connection;
import main.Peer;
import main.ChannelThreads;

public class testSend {
	public static void main(String args[]){
		String param[] = {"224.0.0.0", "4555", "224.0.0.3", "8032", "224.0.0.3", "8033", "64"};
		Peer peer1 = new Peer(param);
		
		ChannelThreads MC = peer1.getMCThread();
		MC.start();
		Connection con = peer1.getMC();
		try {
			con.send("PUTCHUNK 1.0 1 b2 8 5 \r\n\r\n abcbdbasdbasdawd");
			/*System.out.println("stored");
			con.send("STORED 1.0 1 b2 0 \r\n\r\n");
			System.out.println("getchunk");
			con.send("GETCHUNK 1.0 1 b2 0 \r\n\r\n");
			System.out.println("chunk");
			con.send("CHUNK 1.0 1 b2 0 \r\n\r\n abcbdbasdbasdawd");
			System.out.println("deleted");
			con.send("DELETE 1.0 1 b2 \r\n\r\n");
			System.out.println("removed");
			con.send("REMOVED 1.0 1 b2 2 \r\n\r\n");*/
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
