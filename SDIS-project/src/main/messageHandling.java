package main;

import java.io.IOException;
import java.net.InetAddress;
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
		if (put.getSenderId().getId() == peer.getSenderId())
			return;
		put.doIt();
		System.out.println("creating stored msg");
		StoredMessage sto = new StoredMessage(put.getMessageVersion(), new SenderId(peer.getSenderId()) ,put.getFileId(), put.getChunkNo());
		Connection con = peer.getMC();
		/*try {
			System.out.println("sending stored msg");
			Thread.sleep(50);
			con.send(sto.toString());
			System.out.println("sents stored msg");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}*/
	}  
	
	private void handSTORED(){
		StoredMessage sto = (StoredMessage)msg;		
		if (sto.getSenderId().getId() == peer.getSenderId())
			return;
	}
	
	private void handGETCHUNK(){
		GetChunkMessage gt = (GetChunkMessage)msg;
		System.out.println("IDS: " + gt.getSenderId().getId() + " -> " + peer.getSenderId());
		if (gt.getSenderId().getId() == peer.getSenderId())
			return;
		ChunkMessage ch = gt.doIt();
		ch.setSenderId(new SenderId(peer.getSenderId()));
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
		if (ch.getSenderId().getId() == peer.getSenderId())
			return;
		System.out.println("Recebi o chunk.");
	}
	
	private void handDELETE(){
		DeleteMessage dl = (DeleteMessage)msg;
	}
	
	private void handREMOVED(){
		RemovedMessage rm = (RemovedMessage)msg;
	}
}
