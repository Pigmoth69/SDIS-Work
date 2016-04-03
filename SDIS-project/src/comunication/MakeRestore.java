package comunication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ConcurrentModificationException;

public class MakeRestore extends Thread{
		private Thread t;
		private String threadName;
		private boolean flag;
		private int chunkNO;
		private byte[] data;
		private String filename;
		
		
	   
		public MakeRestore(String filename,int chunkNO,byte[] data,String threadName){
			this.threadName = threadName;
			this.chunkNO = chunkNO;
			this.filename=filename;
			this.data=data;
			this.flag=true;
		}
		
		//SÓ NÃO DÁ ERRO PORQUE COMENTEI A PARTE E.PRINTTREE DA CONCURRENT
		public void run() {
				try {
					RandomAccessFile fileOutput = new RandomAccessFile(filename, "rw");

					FileLock isLocked;
					while(flag){
						try {
							fileOutput = new RandomAccessFile(filename,"rw");
							isLocked = fileOutput.getChannel().tryLock();
							
							if(isLocked == null) //file it is locked!!
								continue;
							else{
								if(data.length!=0){
								fileOutput.seek((long)((chunkNO-1)*data.length));
								fileOutput.write(data);
								}
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
			System.out.println("Finished thread: "+threadName);
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