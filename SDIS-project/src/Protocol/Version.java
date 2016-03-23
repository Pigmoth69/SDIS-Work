package Protocol;

public class Version {
	byte Byte1;
	byte Byte2;

	public Version(byte parseByte, byte parseByte2) {
		Byte1 = parseByte;
		Byte2 = parseByte2;
	}
	
	@Override
	public String toString(){
		String res = "" + Byte1 + "." + Byte2;
		return res;
	}
	

}
