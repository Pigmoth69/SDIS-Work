package test;

import java.io.IOException;

import comunication.ChannelThreads;
import comunication.Connection;
import comunication.Peer;

public class testSend {
	public static void main(String args[]){
		String param[] = {"224.0.0.19", "8888", "224.0.0.3", "8032", "224.0.0.3", "8033", "64"};
		Peer peer1 = new Peer(param);
		
		ChannelThreads MC = peer1.getMCThread();
		ChannelThreads MDR = peer1.getMDRThread();
		MC.start();
		MDR.start();
		
		Connection con = peer1.getMC();
		try {
			//con.send("PUTCHUNK 1.0 1 b2 8 5 \r\n\r\n fd94ffff".getBytes());
			
			//con.send(("GETCHUNK 1.0 "+peer1.getSenderId()+" 8024415A728490672CFA476712F23FBB3C58167B330C9538B7FB9BF83A50154B"+" 1 \r\n\r\n").getBytes());
			
			
			//con.send("STORED 1.0 1 b2 0 \r\n\r\n");
			
			//con.send("GETCHUNK 1.0 1 b2 0 \r\n\r\n");
			
			//con.send("CHUNK 1.0 1 b2 0 \r\n\r\n abcbdbasdbasdawd");
			
			//con.send("DELETE 1.0 1 DB06D010D1894B2A732B8A33CDFBD38E9F80A815D9B8517D7EFB6F092F45E431 \r\n\r\n ".getBytes());
			
			con.send("REMOVED 1.0 1 b2 2 \r\n\r\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
