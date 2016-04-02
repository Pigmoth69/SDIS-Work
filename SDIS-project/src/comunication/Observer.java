package comunication;

import java.util.Hashtable;

import Protocol.ChunkId;


abstract class Observer { 
	protected MessageSubject subj; 
	public abstract void update();
	
	ChunkId ckId;
	int responses;
	
	public int getResponses() {
		return responses;
	}

	public void setResponses(int responses) {
		this.responses = responses;
	}

	public ChunkId getCkId() {
		return ckId;
	}

	public void setCkId(ChunkId ckId) {
		this.ckId = ckId;
	}
	

	
}
