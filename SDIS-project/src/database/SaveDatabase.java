package database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ConcurrentModificationException;

import database.Info;

public class SaveDatabase extends Thread{
		private Thread t;
		private String threadName;
		private String path;
		private boolean flag;
		private Info info;
		
	   
		public SaveDatabase(String path,Info info,String threadName){
			this.threadName = threadName;
			this.path = path;
			this.flag=true;
			this.info = info;
		}
		
		//SÓ NÃO DÁ ERRO PORQUE COMENTEI A PARTE E.PRINTTREE DA CONCURRENT
		public void run() {
			
				try {
					FileOutputStream  fileOutput= new FileOutputStream(path);
					FileLock isLocked;
					while(flag){
						try {
							fileOutput = new FileOutputStream(path);
							isLocked = fileOutput.getChannel().tryLock();
							
							if(isLocked == null){ //file it is locked!!
								continue;
							}
							else{
								ObjectOutputStream out = new ObjectOutputStream(fileOutput);
						        out.writeObject(info);
						        out.close();
						        System.out.println("database saved in " + path);
								flag = false; // to exit cicle
							}
						    try {
						    	
						    } finally {
						    	isLocked.release();
						    }
						}catch(OverlappingFileLockException e){
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}catch(ConcurrentModificationException e){
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						} finally {
							try {
								fileOutput.close();
							} catch (IOException e) {
								//e.printStackTrace();
							}
						}
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			//System.out.println("Finished thread: "+threadName);
		}
	   
		public void start ()
		{
			//System.out.println("Starting " +  threadName );
			if (t == null)
			{
				t = new Thread (this, threadName);
				t.start ();
			}
		}
	

}