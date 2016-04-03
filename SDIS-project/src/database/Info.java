package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
	Hashtable<String, String> FileNames;
	
	
	public Hashtable<String, String> getFileNames() {
		return FileNames;
	}

	public void setFileNames(Hashtable<String, String> fileNames) {
		FileNames = fileNames;
	}

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

	public int getChunkIndex(String fileId, int chunkNo){
		Chunk ck;
		
		for (int i = 0; i < this.ChunksSaved.size(); i++){
			ck = this.ChunksSaved.get(i);
			if (ck.getFileId().equals(fileId) && ck.getChunkNo() == chunkNo){
				return i;
			}
		}
		return -1;
	}
	
	public int getChunksNumOfFileId(String fileId){
		Chunk ck;
		int count = 0;
		for (int i = 0; i < this.ChunksSaved.size(); i++){
			ck = this.ChunksSaved.get(i);
			if (ck.getFileId().equals(fileId)){
				count++;
			}
		}
		return count;
	}
	
	public Info(int TotalMemory, int UsedMemory, ArrayList<Chunk> ChunksSaved, Hashtable<String, Integer> FileRep, Hashtable<String, String> FileNames){
		this.TotalMemory = TotalMemory;
		this.UsedMemory = UsedMemory;
		this.ChunksSaved = ChunksSaved;
		this.FileRep = FileRep;
		this.FileNames = FileNames;
	}
	
	public Info(){
		this.TotalMemory = 40000;
		this.UsedMemory = 0;
		this.ChunksSaved = new ArrayList<Chunk>();
		this.FileRep = new Hashtable<String, Integer>();
		this.FileNames = new Hashtable<String, String>();

		Collections.synchronizedList(this.ChunksSaved);
	}
}
