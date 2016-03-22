package Protocol;

public class DeleteMessage  extends Message{
	Version messageVersion;
	SenderId senderId;
	char[] fileId;

	public DeleteMessage(Version messageVersion, SenderId senderId,
			char[] fileId) {
		this.messageVersion = messageVersion;
		this.senderId = senderId;
		this.fileId = fileId;
	}

}
