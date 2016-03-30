package MessageHandling;

import java.io.File;

import Protocol.SenderId;
import Protocol.Version;

public class DeleteMessage  extends Message{
	Version messageVersion;
	SenderId senderId;
	char[] fileId;

	public DeleteMessage(Version messageVersion, SenderId senderId,
			char[] fileId) {
		this.messageVersion = messageVersion;
		this.senderId = senderId;
		this.fileId = fileId;
		this.setType("DELETE");
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

	public void doIt() {
		File ChunkDir = new File("Chunks");
		if (!(ChunkDir.exists() && ChunkDir.isDirectory())) {
			System.out.println("Directory Chunks does not exist");
			return;
		}
		File FileDir = new File("Chunks//" + new String(this.fileId));
		if (!(FileDir.exists() && FileDir.isDirectory())) {
			System.out.println("File directory: " + "/Chunks/" + new String(this.fileId) + " does not exist");
			return;
		}
		System.out.println("Starting to Delete file " + new String(this.fileId) + " chunks directory");
		String[]entries = FileDir.list();
		for(String s: entries){
			System.out.println("Deleting " + s + " chunk");
		    File currentFile = new File(FileDir.getPath(),s);
		    currentFile.delete();
		}
		FileDir.delete();
		System.out.println(new String(this.fileId) + " chunks directory deleted");
	}

}
