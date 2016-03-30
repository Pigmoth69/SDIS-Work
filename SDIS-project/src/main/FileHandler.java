package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileHandler {

	private OutputStream fileOutput;
	private int chunkNO;
	
	public FileHandler(String filename) throws FileNotFoundException {
		File restoreDir = new File("Restore");
		if (!(restoreDir.exists() && restoreDir.isDirectory())) {
			System.out.println("Creating restore directory");
			restoreDir.mkdir();
		}else
			System.out.println("Directory-Restore-already exists!");
		fileOutput = new FileOutputStream("Restore//"+filename);
		chunkNO = 0;
	}
	
	public void writeOnFile(byte[] body){
		try {
			System.out.println("Writing restore file:..");
			System.out.println("bytes.length = " + body.length);
			for(int x=0; x < body.length ; x++){
				fileOutput.write( body[x] ); // writes the bytes
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void closeFileOutput() throws IOException{
		fileOutput.close();
	}
	

	

}
