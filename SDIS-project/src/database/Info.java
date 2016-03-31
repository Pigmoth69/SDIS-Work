package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import main.Chunk;

public class Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int TotalMemory;
	int UsedMemory;
	ArrayList<Chunk> ChunksSaved;
	
	public int getTotalMemory() {
		return TotalMemory;
	}

	public void setTotalMemory(int totalMemory) {
		TotalMemory = totalMemory;
	}

	public int getUsedMemory() {
		return UsedMemory;
	}

	public void setUsedMemory(int usedMemory) {
		UsedMemory = usedMemory;
	}

	public ArrayList<Chunk> getChunksSaved() {
		return ChunksSaved;
	}

	public void setChunksSaved(ArrayList<Chunk> chunksSaved) {
		this.ChunksSaved = chunksSaved;
	}

	public int getChunkIndex(char[] fileId, int chunkNo){
		Chunk ck;
		for (int i = 0; i < ChunksSaved.size(); i++){
			ck = ChunksSaved.get(i);
			if (ck.getFileId().equals(fileId) && ck.getChunkNo() == chunkNo){
				return i;
			}
		}
		return -1;
	}
	
	public Info(int TotalMemory, int UsedMemory, ArrayList<Chunk> ChunksSaved){
		this.TotalMemory = TotalMemory;
		this.UsedMemory = UsedMemory;
		this.ChunksSaved = ChunksSaved;
	}
}
