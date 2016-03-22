package Protocol;

public class RemovedMessage  extends Message{
	Version messageVersion;
	SenderId senderId;
	char[] fileId;
	int chunkNo;
	
	public RemovedMessage(Version messageVersion, SenderId senderId,
			char[] fileId, int chunkNo) {
		this.messageVersion = messageVersion;
		this.senderId = senderId;
		this.fileId = fileId;
		this.chunkNo = chunkNo;
	}

}
