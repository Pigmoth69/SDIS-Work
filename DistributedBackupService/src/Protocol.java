
public class Protocol {
	
	public static String CRLF = "/r/n";

	public message parseMessage(byte[] msg){
		String s1 = msg.toString();
		String[] splited = s1.split(CRLF + CRLF);
		
		String header1 = splited[0];
		String body = splited[1];
		
		
		//faltam mais splits, porque aparentemente
		//uma mensagem pode ter varios headers. Verificar mais tarde
		String inputs[] = header1.split(CRLF);
		String header[] = inputs[0].split("\\s+");
		//^^isto
		
		return processMessage(header, body);
	}

	private message processMessage(String[] header, String body) {
		switch(header[0]){
		case "PUTCHUNK":
			
		}
		
		return null;
	}
}
