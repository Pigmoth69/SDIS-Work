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

	public void Save(){
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream("/database/info.db");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this.info);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in /database/info.db");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public void Load(){
		try
	      {
	         FileInputStream fileIn = new FileInputStream("/database/info.db");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         this.info = (Info) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return;
	      }
	}
}
