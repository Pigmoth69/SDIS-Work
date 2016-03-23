package Protocol;

public class ChunkMessage extends Message{
	Version messageVersion; 
	SenderId senderId;
	char[] fileId;
	int chunkNo;
	byte[] bytes;

	public ChunkMessage(Version messageVersion, SenderId senderId,
			char[] fileId, int chunkNo, byte[] bytes) {
		this.messageVersion = messageVersion;
		this.senderId = senderId;
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.bytes = bytes;
		this.setType("CHUNK");
	}

}
