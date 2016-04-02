package comunication;

import java.util.ArrayList;

import Protocol.ChunkId;

public class MessageSubject {
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private int totalObs = 0;
	private String messageType, newType;
	ChunkId ckId;
	public void attach( Observer o ) {
		observers.add(o);
		totalObs++;
	}
	
	public String getType() {
		return this.messageType;
	}  
	  
	public String getNewType() {
		return newType;
	}

	public void setNewType(String newType, ChunkId ck) {
		this.ckId = ck;
		this.newType = newType;
		notifyAllObservers();
	}

	public ChunkId getCkId() {
		return ckId;
	}

	public void setCkId(ChunkId ckId) {
		this.ckId = ckId;
	}

	public void setType( String in ) {
		this.messageType = in;
	}
	
	public void notifyAllObservers(){
	      for (Observer observer : observers) {
	    	 //System.out.println("Antes:" + observer.getResponses());
	         observer.update();
	         //System.out.println("Depois:" + observer.getResponses());
	      }
	   } 	
}
