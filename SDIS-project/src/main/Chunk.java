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
	int replicationDeg;
	HashSet<String> peers = new HashSet<String>();


	public Chunk(char[] fileId, int chunkNo, int replicationDeg, HashSet<String> peers){
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.replicationDeg = replicationDeg;
		this.peers = peers;
	}
	
	public HashSet<String> getPeers() {
		return peers;
	}


	public void setPeers(HashSet<String> peers) {
		this.peers = peers;
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

	public int getReplicationDeg() {
		return replicationDeg;
	}

	public void setReplicationDeg(int replicationDeg) {
		this.replicationDeg = replicationDeg;
	}
}
