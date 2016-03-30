package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import main.FileHandler;

public class FileTestMiddleWrite {

	static OutputStream os;
	
	
	public static void main(String[] args) throws IOException {
		String filename = "c.png";

		FileHandler h = new FileHandler(filename);
		
		for(int i = 1 ; i <= 20;i++){
			File file = new File("Chunks//DB06D010D1894B2A732B8A33CDFBD38E9F80A815D9B8517D7EFB6F092F45E431//"+i+".chk");
			FileInputStream f = new FileInputStream(file);
			int totalFileSize = (int)file.length();
			byte[] buffer = new byte[totalFileSize];
			f.read(buffer);
			h.writeOnFile(buffer);
			}
		h.closeFileOutput();
		}



	}
	
	/*public static byte[] getData(int chunNO) throws IOException{
		
		String bytes;
		FileInputStream f = new FileInputStream("Chunks//8024415A728490672CFA476712F23FBB3C58167B330C9538B7FB9BF83A50154B//"+chunkNO+".chk");
		
		if(f.available()!=0)
			System.out.println("existe..");
		else
			System.out.println("não existe..");
		

		return null;
	}

}*/
