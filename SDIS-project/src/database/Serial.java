package database;

import java.io.*;

public class Serial{
	
	Info info;
	
	public Serial(Info info){
		this.info = info;
	}
	
	public Serial(){
		
	}
	
	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public synchronized void Save(String path){
		SaveDatabase db = new SaveDatabase(path,this.info,"SaveDatabase");
		db.start();
	}
	
	public synchronized boolean Load(String path){
		try{
	         FileInputStream fileIn = new FileInputStream(path);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         this.info = (Info) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return false;
	      }catch(ClassNotFoundException c){
	         c.printStackTrace();
	         return false;
	      }
		
		return true;
	}
}
