package test;

import java.util.ArrayList;
import java.util.HashMap;

import main.Chunk;
import database.Info;
import database.Serial;

public class testDB {
	public static void main(String args[]){
		if (args[0] == "0"){
			HashMap<String, String> FilesShared = new HashMap<String, String>();
			FilesShared.put("teste.png", "ABCDGFJA111");
			FilesShared.put("teste.txt", "ABCDGFJA222");
			FilesShared.put("teste.mp4", "ABCDGFJA333");
			
			ArrayList<Chunk> ChunksSaved = new ArrayList<Chunk>();
			ChunksSaved.add(new Chunk("ABCDGFJA111".toCharArray(), 1, 3));
			ChunksSaved.add(new Chunk("ABCDGFJA111".toCharArray(), 2, 3));
			ChunksSaved.add(new Chunk("ABCDGFJA111".toCharArray(), 3, 3));
			
			Info info = new Info(5000, 500, FilesShared, ChunksSaved);
			
			Serial s = new Serial(info);
			s.Save("../database/info.db");
		}
		else{
			Serial s = new Serial();
			s.Load("../database/info.db");
			
			Info info = s.getInfo();
			
			Chunk ck = info.getChunksSsaved().get(1);
			System.out.println(ck.getFileId().toString() + " -> " + ck.getChunkNo());
		}
		
	}
}
