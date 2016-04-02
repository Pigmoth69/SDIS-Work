package comunication;

public class putchunkObserver extends Observer{
	
	

	public putchunkObserver(MessageSubject s){
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
