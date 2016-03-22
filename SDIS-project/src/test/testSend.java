package test;

import java.io.IOException;

import main.Connection;
import main.Peer;
import main.Threads;

public class testSend {
	public static void main(String args[]){
		String param[] = {"224.0.0.0", "4555", "224.0.0.3", "8032", "224.0.0.3", "8033", "64"};
		Peer peer1 = new Peer(param);
		
		Threads MC = peer1.getMCThread();
		MC.start();
		Connection con = peer1.getMC();
		try {
			con.send("string de teste, será que funca?");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
