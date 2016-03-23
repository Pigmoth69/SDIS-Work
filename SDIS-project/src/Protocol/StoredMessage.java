package Protocol;

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

}
