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

	public void Save(String path){
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream(path);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this.info);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in " + path);
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public boolean Load(String path){
		try
	      {
	         FileInputStream fileIn = new FileInputStream(path);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         this.info = (Info) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return false;
	      }catch(ClassNotFoundException c)
	      {
	         c.printStackTrace();
	         return false;
	      }
		
		return true;
	}
}
