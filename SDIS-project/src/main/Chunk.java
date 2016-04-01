package main;

import java.io.Serializable;
import java.util.HashSet;

public class Chunk implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int chunkNo;
	char[] fileId;
	HashSet<String> peers = new HashSet<String>();


	public Chunk(char[] fileId, int chunkNo, HashSet<String> peers){
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.peers = peers;
	}
	
	@Override
	public boolean equals(Object ck){
		if (!(ck instanceof Chunk))
			return false;
		
		Chunk ck1 = (Chunk)ck;
		
		if (this.fileId.equals(ck1.getFileId()) && this.chunkNo == ck1.getChunkNo())
			return true;
		else
			return false;
	}

	
	public HashSet<String> getPeers() {
		return peers;
	}


	public void setPeers(HashSet<String> peers) {
		this.peers = peers;
	}
	
	public void addPeer(String peer){
		this.peers.add(peer);
	}


	public int getChunkNo() {
		return chunkNo;
	}

	public void setChunkNo(int chunkNo) {
		this.chunkNo = chunkNo;
	}

	public char[] getFileId() {
		return fileId;
	}

	public void setFileId(char[] fileId) {
		this.fileId = fileId;
	}

}
