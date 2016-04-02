package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class ThreadFileWrite extends Thread{
		private Thread t;
		private String threadName;
		private File file;
		private boolean flag;
		private String message;
		
	   
		public ThreadFileWrite(File file,String message,String threadName){
			this.threadName = threadName;
			this.file = file;
			this.flag=true;
			this.message = message;
		}
		
		
		public void run() {
				try {
					FileOutputStream  fileOutput= new FileOutputStream(file);
					FileLock isLocked;
					while(flag){
						try {
							fileOutput = new FileOutputStream(file);
							isLocked = fileOutput.getChannel().tryLock();
							if(isLocked == null) //file it is locked!!
								continue;
							else{
								
								System.out.println("Write msg 1 on file..."+threadName);
								fileOutput.write(("1-> "+message).getBytes());
								Thread.sleep(5000);
								System.out.println("Write msg 1 on file..."+threadName);
								fileOutput.write(("2-> "+message).getBytes());
								Thread.sleep(5000);
								System.out.println("Write msg 1 on file..."+threadName);
								fileOutput.write(("3-> "+message).getBytes());
								Thread.sleep(5000);
								flag = false; // to exit cicle
							}
						    try {
						    	
						    } finally {
						    	isLocked.release();
						    }
						}catch(OverlappingFileLockException e){
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						} catch (InterruptedException e) {
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
