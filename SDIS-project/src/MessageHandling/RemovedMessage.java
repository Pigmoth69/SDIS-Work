package MessageHandling;

import Protocol.SenderId;
import Protocol.Version;

public class RemovedMessage  extends Message{
	Version messageVersion;
	SenderId senderId;
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

	char[] fileId;
	int chunkNo;
	
	public RemovedMessage(Version messageVersion, SenderId senderId,
			char[] fileId, int chunkNo) {
		this.messageVersion = messageVersion;
		this.senderId = senderId;
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.setType("REMOVED");
	}

}
