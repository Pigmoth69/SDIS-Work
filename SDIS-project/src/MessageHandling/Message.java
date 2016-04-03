package MessageHandling;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import database.Info;
import Protocol.SenderId;
import Protocol.Version;

public class Message {
	
	public static final String CRLF = "\r\n";
	
	private String type;
	private Info info;
	
	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static Message parseMessage(byte[] data){
		String dataString = new String(data);
		String[] dataArgs = dataString.split(CRLF+CRLF); //separates the message into header [0] and body [1] OMG... FDS faltava a merda de um " " para qe funcionasse -.-
		//System.out.println("Args");
		//System.out.println(dataArgs.length);
		//System.out.println(dataArgs[0]);
		String[] inputHeaders = dataArgs[0].split(CRLF);  //only the header at[0] will be processed and the other are considered erroneous
		String[] header = inputHeaders[0].split("\\s+"); //separates all header parts
		
		byte[] body = null;
		if(dataArgs.length > 1){
			int headerTam = dataArgs[0].length();
			body = Arrays.copyOfRange(data, headerTam+4, data.length);
			//byte[] example = Arrays.copyOfRange(body, 0, 50);
			//System.out.println(new String(body));
		}
		
		
		return getMessageType(header, body);		
		
		
	}

	private static Message getMessageType(String[] header, byte[] body) {
		switch(header[0]){
			case "PUTCHUNK":
				return parsePUTCHUNK(header, body);
			case "STORED":
				return parseSTORED(header);
			case "GETCHUNK":
				return parseGETCHUNK(header);
			case "CHUNK":
				return parseCHUNK(header, body);
			case "DELETE":
				return parseDELETE(header);
			case "REMOVED":
				return parseREMOVED(header);
			default: break;
		}
		
		return null;
	}
	
	private static PutChunkMessage parsePUTCHUNK(String header[], byte[] body) {
		
		if(header.length != 6)
			return null;
		
		String[] versionValues = header[1].split("\\.");
		
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		String fileId = header[3];
		
		int chunkNo = Integer.parseInt(header[4]);
		
		int replicationDeg = Integer.parseInt(header[5]);
		
		
		return new PutChunkMessage(messageVersion, senderId, fileId, chunkNo, replicationDeg, body);
	}
	
	private static StoredMessage parseSTORED(String[] header) {
		if(header.length != 5)
			return null;
		
		
		String[] versionValues = header[1].split("\\.");
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		String fileId = header[3];
		int chunkNo = Integer.parseInt(header[4]);
		
		return new StoredMessage(messageVersion, senderId, fileId, chunkNo);
	}
	
	private static GetChunkMessage parseGETCHUNK(String[] header) {
		if(header.length != 5)
			return null;
		
		
		String[] versionValues = header[1].split("\\.");
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		String fileId = header[3];
		int chunkNo = Integer.parseInt(header[4]);
		
		return new GetChunkMessage(messageVersion, senderId, fileId, chunkNo);
	}
	
	private static ChunkMessage parseCHUNK(String[] header, byte[] body){
		System.out.println("Vou fazer o parse do chunk1!");
		if(header.length != 5)
			return null;
		System.out.println("Tamanho do body: "+body.length);
		byte[] copy= new byte[body.length-1001];
		System.arraycopy(body, 1, copy, 0, body.length-1001);
		System.out.println("Tamanho do copy: "+copy.length);
		
		String[] versionValues = header[1].split("\\.");
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		String fileId = header[3];
		
		int chunkNo = Integer.parseInt(header[4]);
		
		return new ChunkMessage(messageVersion, senderId, fileId, chunkNo, copy);
	}

	private static DeleteMessage parseDELETE(String[] header) {
		if(header.length != 4)
			return null;
		
		
		String[] versionValues = header[1].split("\\.");
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		String fileId = header[3];
		
		return new DeleteMessage(messageVersion, senderId, fileId);
	}

	private static RemovedMessage parseREMOVED(String[] header) {
		if(header.length != 4)
			return null;
		
		
		String[] versionValues = header[1].split("\\.");
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		String fileId = header[3];
		
		int chunkNo = Integer.parseInt(header[4]);
		
		return new RemovedMessage(messageVersion, senderId, fileId, chunkNo);
	}

}
