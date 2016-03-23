package main;

import java.io.IOException;

import Protocol.Message;
import Protocol.PutChunkMessage;

public class ChannelThreads extends Thread{
	private Thread t;
	private String threadName;
	private Peer peer;
	private Connection connection;
   
	ChannelThreads(Peer peer, String name){
		this.peer = peer;
		threadName = name;
		System.out.println("Creating " +  threadName );
		switch(name){
			case "MC":
				connection = peer.getMC();
				break;
			case "MDB":
				connection = peer.getMDB();
				break;
			case "MDR":
				connection = peer.getMDR();
				break;
		}
	}
	
	
	public void run() {
		String str = "Nothing received";
		
		System.out.println("Running " +  threadName );
		while(true){
			try {
				System.out.println("Vou tentar receber coisas...");
				str = connection.receive();
				System.out.println("Recebi!");
				Message msg = Message.parseMessage(str.getBytes());
				messageHandling msgRec = new messageHandling(msg, msg.getType());
				msgRec.start();
				/*
				PutChunkMessage b = (PutChunkMessage)c;
				System.out.println("ChunkNO: "+b.getChunkNO());*/
				
					
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.out.println("Thread " +  threadName + " interrupted.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Thread " +  threadName + " exiting.");
			
			if (!str.equals("Nothing received")){
				//System.out.println("Message received successfuly: " + str);
			}
			else{
				System.out.println("Error receiving!");
			}
		}
	}
   
	public void start ()
	{
		System.out.println("Starting " +  threadName );
		if (t == null)
		{
			t = new Thread (this, threadName);
			t.start ();
		}
	}
}
