package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import main.Chunk;

public class Info implements Serializable{
	int TotalMemory;
	int UsedMemory;
	HashMap<String, String> FilesShared;
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

	public HashMap<String, String> getFilesShared() {
		return FilesShared;
	}

	public void setFilesShared(HashMap<String, String> filesShared) {
		FilesShared = filesShared;
	}

	public ArrayList<Chunk> getChunksSsaved() {
		return ChunksSsaved;
	}

	public void setChunksSsaved(ArrayList<Chunk> chunksSsaved) {
		ChunksSsaved = chunksSsaved;
	}

	ArrayList<Chunk> ChunksSsaved;
	
	public Info(int TotalMemory, int UsedMemory, HashMap<String, String> FilesShared, ArrayList<Chunk> ChunksSsaved){
		this.TotalMemory = TotalMemory;
		this.UsedMemory = UsedMemory;
		this.FilesShared = FilesShared;
		this.ChunksSsaved = ChunksSsaved;
	}
}
