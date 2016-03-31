package MessageHandling;

import Protocol.SenderId;
import Protocol.Version;

public class StoredMessage  extends Message{
	Version messageVersion; 
	SenderId senderId;
	char[] fileId;
	int chunkNo;

	public StoredMessage(Version messageVersion, SenderId senderId,
			char[] fileId, int chunkNo) {
		this.messageVersion = messageVersion;
		this.senderId = senderId;
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.setType("STORED");
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

	@Override
	public String toString() {
		return "STORED " + messageVersion.toString() + " " + senderId.getId() + " " + new String(fileId) + " " + chunkNo + " \r\n\r\n";
	}
	
	public void doIt(){
		
	}

}
