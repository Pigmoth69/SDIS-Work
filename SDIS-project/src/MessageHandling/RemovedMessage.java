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

	String fileId;
	int chunkNo;
	
	public RemovedMessage(Version messageVersion, SenderId senderId,
			String fileId, int chunkNo) {
		this.messageVersion = messageVersion;
		this.senderId = senderId;
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.setType("REMOVED");
	}
	
	@Override
	public String toString(){
		return "REMOVED " + this.messageVersion + " " + this.senderId + " " + this.fileId + " " + this.chunkNo + " \r\n\r\n";
	}

}
