package comunication;

import java.util.Hashtable;

import Protocol.ChunkId;

public class getchunkObserver extends Observer{
	

	public getchunkObserver(MessageSubject s, String fileId, int ChunkNo){
		subj = s;
		subj.attach(this);
		responses = 0;
		ckId = new ChunkId(fileId, ChunkNo);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (subj.getNewType().equals("CHUNK") && subj.getCkId().equals(ckId)){
			responses++;
		}
	}
}
