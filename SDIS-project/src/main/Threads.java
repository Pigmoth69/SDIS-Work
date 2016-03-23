package main;

import java.io.IOException;

import Protocol.Message;
import Protocol.PutChunkMessage;

public class Threads extends Thread{
	private Thread t;
	private String threadName;
	private Peer peer;
	private Connection connection;
   
	Threads(Peer peer, String name){
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
		String msg = "Nothing received";
		
		System.out.println("Running " +  threadName );
		while(true){
			try {
				System.out.println("Vou tentar receber coisas...");
				msg = connection.receive();
				System.out.println("Recebi!");
				/*Object c = Message.parseMessage(msg.getBytes());
				PutChunkMessage b = (PutChunkMessage)c;
				System.out.println("ChunkNO: "+b.getChunkNO());*/
				
					
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.out.println("Thread " +  threadName + " interrupted.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Thread " +  threadName + " exiting.");
			
			if (!msg.equals("Nothing received")){
				System.out.println("Message received successfuly: " + msg);
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
