package Protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GetChunkMessage  extends Message{
	Version messageVersion;
	SenderId senderId;
	char[] fileId;
	int chunkNo;

	public GetChunkMessage(Version messageVersion, SenderId senderId,
			char[] fileId, int chunkNo) {
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
					InputStream is = new FileInputStream("Chunks//" + new String(this.fileId) + "//" + chunkNo + ".chk");
				    int size = is.available();
				    byte bytes[] = new byte[65000];
		
				    for(int i=0; i< size; i++){
				       bytes[i] = (byte)is.read();
				    }
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

}
