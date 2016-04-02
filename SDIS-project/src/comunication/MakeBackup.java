package comunication;

import java.io.IOException;

public class MakeBackup extends Thread{
	private Thread t;
	private String threadName;
	private Peer peer;
	Connection c;
	byte[] sendData;
	private MessageSubject subj;
	private putchunkObserver putObs;
	private int repDegree; 
   
	MakeBackup(String type,int repDegree,Peer peer,Connection c, byte[] data){
		this.peer = peer;
		this.c = c;
		this.sendData = data;
		this.repDegree = repDegree;
		
		subj = peer.getSubj();
     	putObs = new putchunkObserver(subj);
     	subj.setType(type);
	}
	
	
	public void run() {
		try {
			
			for(int i = 1 ; i <= 5 ; i++){
				c.send(sendData);
				Thread.sleep(i*1000);
				if(putObs.getResponses() >= repDegree)
					this.stop();
				
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