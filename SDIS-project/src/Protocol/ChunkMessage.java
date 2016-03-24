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
	
	public Version getMessageVersion() {
		return messageVersion;
	}

	public void setMessageVersion(Version messageVersion) {
		this.messageVersion = messageVersion;
	}

	public SenderId getSenderId() {
		return senderId;
	}

	public void setSenderId(SenderId senderId) {
		this.senderId = senderId;
	}

	public char[] getFileId() {
		return fileId;
	}

	public void setFileId(char[] fileId) {
		this.fileId = fileId;
	}

	public int getChunkNo() {
		return chunkNo;
	}

	public void setChunkNo(int chunkNo) {
		this.chunkNo = chunkNo;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public String toString() {
		return "CHUNK " + messageVersion.toString() + " " + senderId.getId() + " " + new String(fileId) + " " + chunkNo + " /r/n/r/n " + new String(bytes);
	}

}
