package comunication;

public class MessageSubject {
	private Observer[] observers = new Observer[9];
	private int totalObs = 0;
	private String messageType, newType;
	public void attach( Observer o ) {
		observers[totalObs++] = o;
	}
	
	public String getType() {
		return this.messageType;
	}  
	  
	public String getNewType() {
		return newType;
	}

	public void setNewType(String newType) {
		this.newType = newType;
		notifyAllObservers();
	}

	public void setType( String in ) {
		this.messageType = in;
	}
	
	public void notifyAllObservers(){
	      for (Observer observer : observers) {
	         observer.update();
	      }
	   } 	
}
