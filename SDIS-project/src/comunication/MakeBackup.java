package comunication;

import java.io.IOException;

public class MakeBackup extends Thread{
	private Thread t;
	private String threadName;
	private Peer peer;
	private int chunkNO;
	private String fileID;
	Connection c;
	byte[] sendData;
	private MessageSubject subj;
	private putchunkObserver putObs;
	private int repDegree; 
   
	public MakeBackup(String type,String fileID,int chunkNO,int repDegree,Peer peer,Connection c, byte[] data){
		this.peer = peer;
		this.c = c;
		this.fileID=fileID;
		this.chunkNO=chunkNO;
		this.sendData = data;
		this.repDegree = repDegree;
		this.threadName = "BackUp Thread";
		
		
		subj = peer.getSubj();
     	putObs = new putchunkObserver(subj,fileID,chunkNO);
     	subj.setType(type);
	}
	
	
	public void run() {
		try {
			for(int i = 0 ; i < 5 ; i++){
				//System.out.println("Repostas Antes: "+putObs.getResponses());
				putObs.setResponses(0);
				//System.out.println("Repostas Depois: "+putObs.getResponses());
				c.send(sendData);
				Thread.sleep((int)Math.pow(2, i)*1000);
				System.out.println("Repostas Recebidas: "+putObs.getResponses() + " RepDegre necess�rio: " + repDegree);
				if(putObs.getResponses() >= repDegree)
					break;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
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