package comunication;


abstract class Observer { 
	protected MessageSubject subj; 
	public abstract void update();
}
