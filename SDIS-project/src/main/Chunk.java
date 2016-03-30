package main;

public class Chunk {
	String data;
	int chunkNo;
	char[] fileId;
	int replicationDeg;
	
	public Chunk(char[] fileId, int chunkNo, String data, int replicationDeg){
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.data = data;
		this.replicationDeg = replicationDeg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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
