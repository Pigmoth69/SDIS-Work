package comunication;

public class getchunkObserver extends Observer{
	private int responses = 0;
	
	public int getResponses() {
		return responses;
	}

	public void setResponses(int responses) {
		this.responses = responses;
	}

	public getchunkObserver(MessageSubject s){
		subj = s;
		subj.attach(this);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (subj.getType().equals("PUTCHUNK") && subj.getNewType().equals("STORED")){
			this.responses++;
		}
	}
}
