package comunication;


abstract class Observer { 
	protected MessageSubject subj; 
	public abstract void update();
	
	protected int responses = 0;
	
	public int getResponses() {
		return responses;
	}

	public void setResponses(int responses) {
		this.responses = responses;
	}
}
