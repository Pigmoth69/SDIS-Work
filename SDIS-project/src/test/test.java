package test;

import main.Peer;
import main.Threads;

public class test {
	public static void main(String args[]){
		String param[] = {"224.0.0.0", "4555", "224.0.0.3", "8032", "224.0.0.3", "8033", "64"};
		Peer peer1 = new Peer(param);
		
		Threads MC = peer1.getMCThread();
		MC.start();
	}
}
