package Protocol;

import Protocol.ChunkMessage;
import Protocol.DeleteMessage;
import Protocol.GetChunkMessage;
import Protocol.PutChunkMessage;
import Protocol.RemovedMessage;
import Protocol.StoredMessage;

public class Message {
	
	public static final String CRLF = "\r\n";
	
	private static String type;
	
	public static Message parseMessage(byte[] data){
		String dataString = new String(data);
		String[] dataArgs = dataString.split(CRLF + CRLF); //separates the message into header [0] and body [1]
		
		String[] inputHeaders = dataArgs[0].split(CRLF);  //only the header at[0] will be processed and the other are considered erroneous
		String[] header = inputHeaders[0].split("\\s+"); //separates all header parts
		
		String body = null;
		if(dataArgs.length > 1)
			body = dataArgs[1];
		
		//returns the message in case of success or null in case of error
		return getMessageType(header, body);		
		
		
	}



	private static Message getMessageType(String[] header, String body) {
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
	


	private static PutChunkMessage parsePUTCHUNK(String header[], String body) {
		System.out.println("PUTCHUNK");
		
		if(header.length != 6)
			return null;
		
		String[] versionValues = header[1].split("\\.");
		
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		char[] fileId = header[3].toCharArray();
		
		int chunkNo = Integer.parseInt(header[4]);
		
		int replicationDeg = Integer.parseInt(header[5]);
		
		/*System.out.println("Version: " + messageVersion.toString() + "; SenderId: " + senderId.getId() + "; fileId: " + fileId[0]
				+ "; chunckNo: " + chunkNo + "; replicationDeg: " + replicationDeg);
		*/
		return new PutChunkMessage(messageVersion, senderId, fileId, chunkNo, replicationDeg, body.getBytes());
	}
	
	
	private static StoredMessage parseSTORED(String[] header) {
		if(header.length != 5)
			return null;
		
		System.out.println("STORED");
		
		String[] versionValues = header[1].split("\\.");
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		char[] fileId = header[3].toCharArray();
		int chunkNo = Integer.parseInt(header[4]);
		
		return new StoredMessage(messageVersion, senderId, fileId, chunkNo);
	}
	

	private static GetChunkMessage parseGETCHUNK(String[] header) {
		if(header.length != 5)
			return null;
		
		System.out.println("GETCHUNK");
		
		String[] versionValues = header[1].split("\\.");
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		char[] fileId = header[3].toCharArray();
		int chunkNo = Integer.parseInt(header[4]);
		
		return new GetChunkMessage(messageVersion, senderId, fileId, chunkNo);
	}
	
	private static ChunkMessage parseCHUNK(String[] header, String body){
		if(header.length != 5)
			return null;
		
		System.out.println("CHUNK");
		
		String[] versionValues = header[1].split("\\.");
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		char[] fileId = header[3].toCharArray();
		
		int chunkNo = Integer.parseInt(header[4]);
		
		return new ChunkMessage(messageVersion, senderId, fileId, chunkNo, body.getBytes());
	}

	private static DeleteMessage parseDELETE(String[] header) {
		if(header.length != 4)
			return null;
		
		System.out.println("DELETE");
		
		String[] versionValues = header[1].split("\\.");
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		char[] fileId = header[3].toCharArray();
		
		return new DeleteMessage(messageVersion, senderId, fileId);
	}

	private static RemovedMessage parseREMOVED(String[] header) {
		System.out.println("REMOVED");
		
		if(header.length != 4)
			return null;
		
		
		String[] versionValues = header[1].split("\\.");
		Version messageVersion = new Version(Byte.parseByte(versionValues[0]), Byte.parseByte(versionValues[1]));
		
		SenderId senderId = new SenderId(header[2]);
		
		char[] fileId = header[3].toCharArray();
		
		int chunkNo = Integer.parseInt(header[4]);
		
		return new RemovedMessage(messageVersion, senderId, fileId, chunkNo);
	}

}
