package comunication;

import java.util.Hashtable;

import Protocol.ChunkId;

public class putchunkObserver extends Observer{
	
	

	public putchunkObserver(MessageSubject s, String fileId, int ChunkNo){
		subj = s;
		subj.attach(this);
		responses = 0;
		ckId = new ChunkId(fileId, ChunkNo);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (subj.getNewType().equals("STORED") && subj.getCkId().equals(ckId)){
			responses++;
		}
	}
}
