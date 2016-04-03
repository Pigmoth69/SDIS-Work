package test;

import comunication.ChannelThreads;
import comunication.Peer;

public class test {
	public static void main(String args[]){
		String param[] = {"224.0.0.19", "8888", "224.0.0.3", "8032", "224.0.0.3", "8033", "65"};
		param[6] = args[0];
		Peer peer1 = new Peer(param);
		
		ChannelThreads MC = peer1.getMCThread();
		MC.start();
		ChannelThreads MDB = peer1.getMDBThread();
		MDB.start();
		ChannelThreads MDR = peer1.getMDRThread();
		MDR.start();
		
	}
}
