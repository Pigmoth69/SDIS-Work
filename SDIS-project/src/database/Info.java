package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import main.Chunk;

public class Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int TotalMemory;
	int UsedMemory;
	ArrayList<Chunk> ChunksSaved;
	Hashtable<String, Integer> FileRep;
	
	public Hashtable<String, Integer> getFileRep() {
		return FileRep;
	}

	public void setFileRep(Hashtable<String, Integer> fileRep) {
		FileRep = fileRep;
	}

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
	
	public void addChunkSaved(Chunk ck){
		this.ChunksSaved.add(ck);
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
	
	public Info(int TotalMemory, int UsedMemory, ArrayList<Chunk> ChunksSaved, Hashtable<String, Integer> FileRep){
		this.TotalMemory = TotalMemory;
		this.UsedMemory = UsedMemory;
		this.ChunksSaved = ChunksSaved;
		this.FileRep = FileRep;
	}
}
