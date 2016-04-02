package comunication;

import java.util.Hashtable;

import Protocol.ChunkId;


abstract class Observer { 
	protected MessageSubject subj; 
	public abstract void update();
	
	Hashtable<ChunkId, Integer> responses;
	
	public Hashtable<ChunkId, Integer> getResponses() {
		return responses;
	}

	public void setResponses(Hashtable<ChunkId, Integer> responses) {
		this.responses = responses;
	}
	
	public void addChunk(ChunkId ck){
		this.responses.put(ck, 0);
	}

	
}
