package Protocol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PutChunkMessage extends Message{
	Version messageVersion;
	SenderId senderId;
	char[] fileId;
	int chunkNo;
	int replicationDeg;
	byte[] bytes;
	
	public PutChunkMessage(Version messageVersion, SenderId senderId,
			char[] fileId, int chunkNo, int replicationDeg, byte[] bytes) {
		this.messageVersion = messageVersion;
		this.senderId = senderId;
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.replicationDeg = replicationDeg;
		this.bytes = bytes;
		this.setType("PUTCHUNK");
	}
	public int getChunkNO(){
		return chunkNo;
	}
	
	public void doIt(){
		File ChunkDir = new File("Chunks");
		if (!(ChunkDir.exists() && ChunkDir.isDirectory())) {
			System.out.println("Creating chunks directory");
			ChunkDir.mkdir();
		}
		File FileDir = new File("Chunks//" + new String(this.fileId));
		if (!(FileDir.exists() && FileDir.isDirectory())) {
			System.out.println("Creating File directory: " + "/Chunks/" + new String(this.fileId));
			FileDir.mkdirs();
		}
		try {
			System.out.println("Writing");
			OutputStream os = new FileOutputStream("Chunks//" + new String(this.fileId) + "//" + chunkNo + ".chk");
			System.out.println("bytes.length = " + bytes.length);
			for(int x=0; x < bytes.length ; x++){
			   os.write( bytes[x] ); // writes the bytes
			}
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
