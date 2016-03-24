package main;

import java.io.IOException;
import java.util.Random;

import Protocol.ChunkMessage;
import Protocol.DeleteMessage;
import Protocol.GetChunkMessage;
import Protocol.Message;
import Protocol.PutChunkMessage;
import Protocol.RemovedMessage;
import Protocol.SenderId;
import Protocol.StoredMessage;
import Protocol.Version;

public class messageHandling extends Thread{
	private Thread t;
	Message msg;
	private String threadName;
	private Peer peer;
	
	messageHandling(Peer peer, Message msg, String name){
		this.msg = msg;
		this.threadName = name;
		this.peer = peer;
		
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
		StoredMessage sto = new StoredMessage(put.getMessageVersion(), put.getSenderId(),put.getFileId(), put.getChunkNo());
		Connection con = peer.getMC();
		try {
			Thread.sleep(50);
			con.send(sto.toString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}  
	
	private void handSTORED(){
		StoredMessage sto = (StoredMessage)msg;		
	}
	
	private void handGETCHUNK(){
		GetChunkMessage gt = (GetChunkMessage)msg;
		ChunkMessage ch = gt.doIt();
		if (ch != null){
			Connection con = peer.getMDR();
			try{
				Random generator = new Random();
		        int number = generator.nextInt(400);
				Thread.sleep(number);
				System.out.println("Enviar chunk");
				con.send(ch.toString());
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handCHUNK(){
		ChunkMessage ch = (ChunkMessage)msg;
		System.out.println("Recebi o chunk.");
	}
	
	private void handDELETE(){
		DeleteMessage dl = (DeleteMessage)msg;
	}
	
	private void handREMOVED(){
		RemovedMessage rm = (RemovedMessage)msg;
	}
}
