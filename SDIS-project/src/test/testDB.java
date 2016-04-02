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
			ChunksSaved.add(new Chunk("ABCDGFJA222", 2, peers));
			ChunksSaved.add(new Chunk("ABCDGFJA333", 3, peers));
			
			Info info = new Info(5000, 50, ChunksSaved, null);
			Serial s = new Serial(info);
			s.Save("database/info.db");
		}else if(args[0].equals("1")){
			HashSet<String> peers = new HashSet<String>();
			ArrayList<Chunk> ChunksSaved = new ArrayList<Chunk>();
			ChunksSaved.add(new Chunk("ABCDGFJA444", 4, peers));
			ChunksSaved.add(new Chunk("ABCDGFJA555", 5, peers));
			ChunksSaved.add(new Chunk("ABCDGFJA666", 6, peers));
			
			Info info = new Info(5000, 50, ChunksSaved, null);
			Serial s = new Serial(info);
			s.Save("database/info.db");
		}
		else{
			Serial s = new Serial();
			s.Load("database/info.db");
			
			Info info = s.getInfo();
			
			Chunk ck1 = info.getChunksSaved().get(2);
			System.out.println(new String(ck1.getFileId()) + " -> " + ck1.getChunkNo());
			Chunk ck2 = info.getChunksSaved().get(3);
			System.out.println(new String(ck2.getFileId()) + " -> " + ck2.getChunkNo());
		}
		
	}
}
