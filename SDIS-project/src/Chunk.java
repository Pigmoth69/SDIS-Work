
public class Chunk {
	String data;
	int chunkNo;
	char[] fileId;
	
	Chunk(char[] fileId, int chunkNo, String data){
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		this.data = data;
	}
}
