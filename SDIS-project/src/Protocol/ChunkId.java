package Protocol;

public class ChunkId {
	private String fileId;
	private int chunkNo;
	
	public ChunkId(String fileId, int chunkNo){
		this.fileId = fileId;
		this.chunkNo = chunkNo;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public int getChunkNo() {
		return chunkNo;
	}

	public void setChunkNo(int chunkNo) {
		this.chunkNo = chunkNo;
	}
	
	@Override
	public boolean equals(Object e){
		ChunkId ck = (ChunkId) e;
		if (this.fileId.equals(ck.getFileId()) && this.chunkNo == ck.getChunkNo()){
			return true;
		}
		else
			return false;
	}
}
