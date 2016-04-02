package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import main.Chunk;
import database.Info;
import database.Serial;

public class testDB {
	public static void main(String args[]){
		if (args[0].equals("0")){
			HashSet<String> peers = new HashSet<String>();
			ArrayList<Chunk> ChunksSaved = new ArrayList<Chunk>();
			ChunksSaved.add(new Chunk("ABCDGFJA111", 1, peers));
			ChunksSaved.add(new Chunk("ABCDGFJA111", 2, peers));
			ChunksSaved.add(new Chunk("ABCDGFJA111", 3, peers));
			
			//Info info = new Info(5000, 50, ChunksSaved);
			//Serial s = new Serial(info);
			//s.Save("database/info.db");
		}
		else{
			Serial s = new Serial();
			s.Load("database/info.db");
			
			Info info = s.getInfo();
			
			Chunk ck = info.getChunksSaved().get(1);
			System.out.println(new String(ck.getFileId()) + " -> " + ck.getChunkNo());
		}
		
	}
}
