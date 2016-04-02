package comunication;

import java.util.Hashtable;

import Protocol.ChunkId;

public class getchunkObserver extends Observer{
	
	public Hashtable<ChunkId, Integer> getResponses() {
		return responses;
	}

	public void setResponses(Hashtable<ChunkId, Integer> responses) {
		this.responses = responses;
	}

	public getchunkObserver(MessageSubject s){
		subj = s;
		subj.attach(this);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (subj.getNewType().equals("CHUNK")){
			int resp = this.responses.get(subj.getCkId());
			this.responses.put(subj.getCkId(), resp+1);
		}
	}
}
