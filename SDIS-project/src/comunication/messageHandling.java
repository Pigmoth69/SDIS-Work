package comunication;

import java.io.IOException;
import java.util.Random;

import MessageHandling.ChunkMessage;
import MessageHandling.DeleteMessage;
import MessageHandling.GetChunkMessage;
import MessageHandling.Message;
import MessageHandling.PutChunkMessage;
import MessageHandling.RemovedMessage;
import MessageHandling.StoredMessage;
import Protocol.SenderId;

public class messageHandling extends Thread{
	private Thread t;
	Message msg;
	private String threadName;
	private Peer peer;
	
	public messageHandling(Peer peer, Message msg, String name){
		this.msg = msg;
		this.threadName = name;
		this.peer = peer;
		
	}
	
	public void run() {
		//System.out.println("Handelling");
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
		//System.out.println("IDS: " + put.getSenderId().getId() + " -> " + peer.getSenderId());
		if (sentByMe(put.getSenderId().getId(), put.getType())){
			return;
		}
		
		
		put.doIt();
		//System.out.println("creating stored msg");
		StoredMessage sto = new StoredMessage(put.getMessageVersion(), new SenderId(peer.getSenderId()) ,put.getFileId(), put.getChunkNo());
		Connection con = peer.getMC();
		try {
			//System.out.println("sending stored msg");
			Thread.sleep(50);
			con.send(sto.toString().getBytes());
			//System.out.println("sent stored msg");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}  
	
	private void handSTORED(){
		StoredMessage sto = (StoredMessage)msg;		
		
		if (sentByMe(sto.getSenderId().getId(), sto.getType())){
			return;
		}
	}
	
	private void handGETCHUNK(){
		GetChunkMessage gt = (GetChunkMessage)msg;
		
		if (sentByMe(gt.getSenderId().getId(), gt.getType())){
			return;
		}
		
		ChunkMessage ch = gt.doIt();
		ch.setSenderId(new SenderId(peer.getSenderId()));
		if (ch != null){
			Connection con = peer.getMDR();
			try{
				Random generator = new Random();
		        int number = generator.nextInt(400);
				Thread.sleep(number);
				
				con.send(ch.toString().getBytes());
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handCHUNK(){
		ChunkMessage ch = (ChunkMessage)msg;
		
		if (sentByMe(ch.getSenderId().getId(), ch.getType())){
			return;
		}
	}
	
	private void handDELETE(){
		DeleteMessage dl = (DeleteMessage)msg;
		
		if (sentByMe(dl.getSenderId().getId(), dl.getType())){
			return;
		}
		dl.doIt();
	}
	
	private void handREMOVED(){
		RemovedMessage rm = (RemovedMessage)msg;
		
		if (sentByMe(rm.getSenderId().getId(), rm.getType())){
			return;
		}
	}
	
	private boolean sentByMe(String id, String type){
		if (id.equals(peer.getSenderId())){
			System.out.println(type + " message was sent by myself. IGNORE!");
			return true;
		}
		else
			return false;
	}
}
