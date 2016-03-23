package main;

import Protocol.ChunkMessage;
import Protocol.DeleteMessage;
import Protocol.GetChunkMessage;
import Protocol.Message;
import Protocol.PutChunkMessage;
import Protocol.RemovedMessage;
import Protocol.StoredMessage;

public class messageHandling extends Thread{
	private Thread t;
	Message msg;
	private String threadName;
	
	messageHandling(Message msg, String name){
		this.msg = msg;
		this.threadName = name;
		
	}
	
	public void run() {
		System.out.println("Handelling");
		switch(msg.getType()){
			case "PUTCHUNK":
				handPUTCHUNK();
				break;
			case "STORED":
				handSTORED();
				break;
			case "GETCHUNK":
				handGETCHUNK();
				break;
			case "CHUNK":
				handCHUNK();
				break;
			case "DELETE":
				handDELETE();
				break;
			case "REMOVED":
				handREMOVED();
				break;
			default: break; 
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
	
	private void handPUTCHUNK(){
		PutChunkMessage put = (PutChunkMessage)msg;
		put.doIt();
	}
	
	private void handSTORED(){
		StoredMessage sto = (StoredMessage)msg;		
	}
	
	private void handGETCHUNK(){
		GetChunkMessage gt = (GetChunkMessage)msg;
	}
	
	private void handCHUNK(){
		ChunkMessage ch = (ChunkMessage)msg;
	}
	
	private void handDELETE(){
		DeleteMessage dl = (DeleteMessage)msg;
	}
	
	private void handREMOVED(){
		RemovedMessage rm = (RemovedMessage)msg;
	}
}
