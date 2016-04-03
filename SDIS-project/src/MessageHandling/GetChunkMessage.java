package MessageHandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Protocol.SenderId;
import Protocol.Version;

public class GetChunkMessage  extends Message{
	Version messageVersion;
	SenderId senderId;
	String fileId;
	int chunkNo;

	public GetChunkMessage(Version messageVersion, SenderId senderId,
			String fileId, int chunkNo) {
		this.messageVersion = messageVersion;
		this.senderId = senderId;
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.setType("GETCHUNK");
	}
	
	@Override
	public String toString() {
		return "GETCHUNK " + messageVersion.toString() + " " + senderId.getId() + " " + new String(fileId) + " " + chunkNo + " /r/n/r/n";
	}
	
	public ChunkMessage doIt(){
		File ChunkDir = new File("Chunks");
		if (!(ChunkDir.exists() && ChunkDir.isDirectory())) {
			System.out.println("Chunks directory non existent");
		}
		else{
			File FileDir = new File("Chunks//" + new String(this.fileId));
			if (!(FileDir.exists() && FileDir.isDirectory())) {
				System.out.println("File directory " + "/Chunks/" + new String(this.fileId) + "non existent");
			}
			else{
				try {
					System.out.println("Getting chunk ");
					File f = new File("Chunks//" + new String(this.fileId) + "//" + chunkNo + ".chk");
					if(!f.exists()){
						System.out.println("FILE DOES NOT EXISTS!!");
						return null;
					}
					FileInputStream is = new FileInputStream(f);
				    byte bytes[] = new byte[(int)f.length()];
				    is.read(bytes);
				    is.close();
				    
				    return new ChunkMessage(messageVersion, senderId, fileId, chunkNo, bytes);
				    
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
		
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

}
