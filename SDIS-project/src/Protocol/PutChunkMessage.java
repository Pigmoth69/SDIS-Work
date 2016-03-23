package Protocol;

public class PutChunkMessage extends Message{
	Version messageVersion;
	SenderId senderId;
	char[] fileId;
	int chunkNo;
	int replicationDeg;
	byte[] bytes;
	
	public PutChunkMessage(Version messageVersion, SenderId senderId,
			char[] fileId, int chunkNo, int replicationDeg, byte[] bytes) {
		this.messageVersion = messageVersion;
		this.senderId = senderId;
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.replicationDeg = replicationDeg;
		this.bytes = bytes;
	}
	public int getChunkNO(){
		return chunkNo;
	}

}
