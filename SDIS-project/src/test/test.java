package test;

import comunication.ChannelThreads;
import comunication.Peer;

public class test {
	public static void main(String args[]){
		String param[] = {"224.0.0.19", "8888", "224.0.0.3", "8032", "224.0.0.3", "8033", "64"};
		Peer peer1 = new Peer(param);
		
		ChannelThreads MC = peer1.getMCThread();
		MC.start();
	}
}
