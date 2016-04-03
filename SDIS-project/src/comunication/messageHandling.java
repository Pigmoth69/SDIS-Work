package comunication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

import sun.security.pkcs11.wrapper.CK_AES_CTR_PARAMS;
import main.Chunk;
import main.Client;
import database.Info;
import database.Serial;
import MessageHandling.ChunkMessage;
import MessageHandling.DeleteMessage;
import MessageHandling.GetChunkMessage;
import MessageHandling.Message;
import MessageHandling.PutChunkMessage;
import MessageHandling.RemovedMessage;
import MessageHandling.StoredMessage;
import Protocol.ChunkId;
import Protocol.SenderId;
import Protocol.Version;

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
			try {
				handREMOVED();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			default: break; 
		}
		
		this.peer.getSerial().Save("database/info.db");
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
		
		
		FileRep.put(put.getFileId(), put.getReplicationDeg());
		
		//Serial serial = peer.getSerial();
		//serial.Save("database/info.db");
		
		
		
		
		
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
		
		if (sentByMe(sto.getSenderId().getId(), sto.getType())){
			return;
		}
		
		Info info = peer.getInfo();
		MessageSubject subj = this.peer.getSubj();
		subj.setNewType("STORED", new ChunkId(sto.getFileId(), sto.getChunkNo()));
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		//Serial serial = peer.getSerial();
		//serial.Save("database/info.db");
	}
	
	private void handGETCHUNK(){
		GetChunkMessage gt = (GetChunkMessage)msg;
		
		MessageSubject subj = peer.getSubj();
    	getchunkObserver getObs = new getchunkObserver(subj, gt.getFileId(), gt.getChunkNo());
    	subj.setType("GETCHUNK");
		
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
				
				if (getObs.getResponses() == 0){
					byte[] copy = new byte[ch.getBytes().length+ch.toString().getBytes().length];
					
					System.arraycopy(ch.toString().getBytes(), 0, copy, 0, ch.toString().getBytes().length);
					System.arraycopy(ch.getBytes(), 0, copy, ch.toString().getBytes().length, ch.getBytes().length);
					con.send(copy);
				}
					
				
				System.out.println(new String(ch.getBytes()).substring(0, 20));
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handCHUNK(){
		ChunkMessage ch = (ChunkMessage)msg;

		MessageSubject subj = peer.getSubj();
		subj.setNewType("CHUNK", new ChunkId(ch.getFileId(), ch.getChunkNo()));

		if (sentByMe(ch.getSenderId().getId(), ch.getType())){
			return;
		}
		
		System.out.println(new String(ch.getBytes()).substring(0, 20));
		
		System.out.println("Recebi um chunk!");

		//isto esta hardcoded mas tem de ser mudado
		String filename = new String("Restore");
		File theDir = new File(filename);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + filename);
			boolean result = false;
			try{
				theDir.mkdir();
				result = true;
			} 
			catch(SecurityException se){
				//handle it
			}        
			if(result)   
				System.out.println("DIR Restre created");  
		}
		String outFIle = peer.getInfo().getFileNames().get(ch.getFileId());
		MakeRestore mk = new MakeRestore(filename + "/" + outFIle,ch.getChunkNo(),ch.getBytes(),"MakeRestore");
		mk.start();



		Info info = peer.getInfo();

	}
	
	private void handDELETE(){
		DeleteMessage dl = (DeleteMessage)msg;
		
		if (sentByMe(dl.getSenderId().getId(), dl.getType())){
			return;
		}
		dl.doIt();
	}
	
	private void handREMOVED() throws IOException{
		RemovedMessage rm = (RemovedMessage)msg;
		
		if (sentByMe(rm.getSenderId().getId(), rm.getType())){
			return;
		}
		
		Info info = this.peer.getInfo();
		int ckId = info.getChunkIndex(rm.getFileId(), rm.getChunkNo());
		if (ckId != -1){
			Chunk ck = info.getChunksSaved().get(ckId);
			ck.removePeer(rm.getSenderId().getId());
			int repDeg = ck.getPeers().size();
			Hashtable<String, Integer> fileRep = info.getFileRep();
			int minRep = fileRep.get(ck.getFileId());
			
			if (repDeg < minRep){
				File ChunkDir = new File("Chunks");
				if (!(ChunkDir.exists() && ChunkDir.isDirectory())) 
					return;
				File FileDir = new File("Chunks//" + rm.getFileId());
				if (!(FileDir.exists() && FileDir.isDirectory())) 
					return;
				File currentFile = new File("Chunks//" + rm.getFileId() + "//" + rm.getChunkNo() + ".chk");
				if (currentFile.exists()){
					System.out.println("iniciar o subprotocolo de backup aqui.");
					
					FileInputStream f = new FileInputStream(currentFile);
					byte[] buffer = new byte[(int) currentFile.length()];
					f.read(buffer);
					int replication = info.getFileRep().get(rm.getFileId());
					String header = "PUTCHUNK "+ rm.getMessageVersion() + " " + peer.getSenderId() + " " + rm.getFileId() + " " + rm.getChunkNo() + " " + replication + " \r\n\r\n"; 
					byte[] sendHeader = header.getBytes();
					
					byte[] copy = new byte[sendHeader.length+buffer.length];
					
					System.arraycopy(sendHeader, 0, copy, 0, sendHeader.length);
					System.arraycopy(buffer, 0, copy, sendHeader.length, buffer.length);
					
					
					MakeBackup mb = new MakeBackup("PUTCHUNK",rm.getFileId(),rm.getChunkNo(), replication, peer, peer.getMDB(), copy);
					mb.start();
					
					
					
				}	
			}
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
