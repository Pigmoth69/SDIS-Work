package comunication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

import sun.security.pkcs11.wrapper.CK_AES_CTR_PARAMS;
import main.Chunk;
import database.Info;
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
		//System.out.println("Starting " +  threadName );
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
		
		Info info = peer.getInfo();
		Hashtable<String, Integer> FileRep = info.getFileRep();
		Integer RepDeg = FileRep.get(new String(put.getFileId()));
		if (RepDeg == null){
			FileRep.put(new String(put.getFileId()), put.getReplicationDeg());
		}
		//System.out.println("creating stored msg");
		StoredMessage sto = new StoredMessage(put.getMessageVersion(), new SenderId(peer.getSenderId()) ,put.getFileId(), put.getChunkNo());
		Connection con = peer.getMC();
		try {
			Random generator = new Random();
	        int number = generator.nextInt(400);
			Thread.sleep(number);
			
			con.send(sto.toString().getBytes());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}  
	
	private void handSTORED(){
		StoredMessage sto = (StoredMessage)msg;		
		
		Info info = peer.getInfo();
		int chunkSavedId = info.getChunkIndex(sto.getFileId(), sto.getChunkNo());
		if (chunkSavedId != -1){
			//Se já existir o chunk gravado
			ArrayList<Chunk> ckSaved = info.getChunksSaved();
			Chunk ck = ckSaved.get(chunkSavedId);
			ck.addPeer(sto.getSenderId().getId());
		}
		else{
			//Se não adiciona o chunk à lista
			ArrayList<Chunk> ckSaved = info.getChunksSaved();
			HashSet<String> peers = new HashSet<String>();
			peers.add(sto.getSenderId().getId());
			Chunk ck = new Chunk(sto.getFileId(), sto.getChunkNo(), peers);
			ckSaved.add(ck);
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
		
		Info info = this.peer.getInfo();
		int ckId = info.getChunkIndex(rm.getFileId(), rm.getChunkNo());
		if (ckId != -1){
			Chunk ck = info.getChunksSaved().get(ckId);
			ck.removePeer(rm.getSenderId().getId());
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
