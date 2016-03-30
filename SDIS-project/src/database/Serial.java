package database;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import main.Chunk;

public class Serial implements Serializable{
	int TotalMemory;
	int UsedMemory;
	HashMap<String, String> FilesShared;
	ArrayList<Chunk> ChunksSsaved;
	
	
}
