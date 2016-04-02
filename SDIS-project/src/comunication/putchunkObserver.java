package comunication;

public class putchunkObserver extends Observer{
	
	

	public putchunkObserver(MessageSubject s){
		subj = s;
		subj.attach(this);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (subj.getNewType().equals("STORED")){
			int resp = this.responses.get(subj.getCkId());
			this.responses.put(subj.getCkId(), resp+1);
		}
	}
}
