package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;

public class Client {
	
	static Peer peer;
	static Threads MC;
	static Connection con;
	static String param[] = {"224.0.0.0", "4555", "224.0.0.3", "8032", "224.0.0.3", "8033", "64"};
	
	public static void main(String[] args) throws NumberFormatException, IOException, NoSuchAlgorithmException {
		if(!checkArgsNum(args))//checks if there is a invalid number of arguments
			return;
		
		int peer_access_point;
		String sub_protocol;
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(args[0]);
		boolean b = m.matches();
		
		if(!b){
			System.out.println("Invalid <peer_ap>!");
			return;
		}
		peer_access_point = Integer.parseInt(args[0]);
		sub_protocol = args[1];
		
		switch(sub_protocol){
			case "BACKUP":
				if(checkBackup(args[2],args[3]) && args.length == 4){
					System.out.println("OK- starting peer..");
					startPeer();
					startBackup(peer_access_point,sub_protocol,args[2],Integer.parseInt(args[3]));
				}
				else
					System.out.println("Invalid <sub_protocol>");
				break;
			case "RESTORE":
				if(checkRestore(args[2]) && args.length == 3){
					System.out.println("OK");
					//handleSubProtocol(peer_access_point,sub_protocol,args[2]);
				}	
				else
					System.out.println("Invalid <sub_protocol>");
				break;
			case "DELETE":
				if(checkDelete(args[2])&& args.length == 3){
					System.out.println("OK");
					//handleSubProtocol(peer_access_point,sub_protocol,args[2]);
				}
				else
					System.out.println("Invalid <sub_protocol>");
				break;
			case "RECLAIM":
				if(checkReclaim() && args.length == 3){
					System.out.println("OK");
					//handleSubProtocol(peer_access_point,sub_protocol,args[2]);
					}
				break;
			default:
				System.out.println("Invalid <sub_protocol>");
				return;
		}
			
	}
	private static void startPeer(){
		peer = new Peer(param);
		MC = peer.getMCThread();
		MC.start();
		con = peer.getMC();
	}
	private static void startBackup(int peer_access_point, String sub_protocol, String filename, int replication) throws IOException, NoSuchAlgorithmException {
		File file = new File(filename);
		double size = file.length()/64000;
		size = Math.ceil(size);
		FileInputStream readFile = new FileInputStream(file);
		
		int chunkNO = 1;
		byte[] tempData;
		String hash = filename+sub_protocol+peer_access_point+replication;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(hash.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        byte[] digest = md.digest();
        String fileId = DatatypeConverter.printHexBinary(digest);
        
		while(readFile.available() > 0){
			if(readFile.available()>= 64000)
				 tempData = new byte[64000];
			else
				tempData = new byte[readFile.available()];
			
			readFile.read(tempData);
			String data = new String(tempData);
			
			String send = new String("PUTCHUNK 1.0 "+peer_access_point+" "+fileId+" "+chunkNO+" "+replication+" \r\n\r\n "+data);
			con.send(send);
			chunkNO++;
		}
		System.out.println("Ending Sending chunks!");
		
		
		
	}

	public static void handleSubProtocol(int peer_access_point,String sub_protocol, String filename,int replication){
		//TODO fazer o handler
		System.out.println("peer_access_point: "+ peer_access_point);
		System.out.println("sub_protocol: "+ sub_protocol);
		System.out.println("filename: "+ filename);
		System.out.println("replication: "+ replication);
		
	}
	public static void handleSubProtocol(int peer_access_point,String sub_protocol, String filename){
		//TODO fazer o handler
		System.out.println("peer_access_point: "+ peer_access_point);
		System.out.println("sub_protocol: "+ sub_protocol);
		System.out.println("filename: "+ filename);
	}
	
	private static boolean checkReclaim() {
		return false;
		// TODO Auto-generated method stub
	}

	private static boolean checkDelete(String filename) {
		if(!checkIfFileExists(filename))
			return false;
		return true;
		
	}

	private static boolean checkRestore(String filename){
		if(!checkIfFileExists(filename))
			return false;
		
			
		return true;
	}

	private static boolean checkBackup(String filename,String r) {
		int replication = Integer.parseInt(r);
		if(replication >9 || replication <0 || !checkIfFileExists(filename)){
			System.out.println("Invalid replication");
			return false; 
			}
		return true;
	}

	public static boolean checkArgsNum(String[] args){
		if(args.length <3 || args.length>4){
			printInvalidArgsNumberHelp();
			return false;
		}
		return true;
	}
	
	public static void printInvalidArgsNumberHelp(){
		System.out.println("Invalid number of arguments!");
		System.out.println("Try one of the following types: ");
		System.out.println("<peer_ap> <sub_protocol> <opnd_1> <opnd_2> ");
		System.out.println("Where: ");
		System.out.println("<peer_ap>");
		System.out.println("Is the local peer access point. This depends on the implementation. (Check the next section)");
		System.out.println("<sub_protocol>");
		System.out.println("Is the sub protocol being tested, and must be one of: BACKUP, RESTORE, DELETE, RECLAIM. In the case of enhancements, you must append the substring ENH at the end of the respecive subprotocol, e.g. BACKUPENH");
		System.out.println("<opnd_1>");
		System.out.println("Is either the path name of the file to backup/restore/delete, for the respective 3 subprotocols, or the amount of space to reclaim. In the latter case, the peer should execute the RECLAIM protocol, upon deletion of any chunk.");
		System.out.println("<opnd_2>");
		System.out.println("This operand is an integer that specifies the desired replication degree and applies only to the backup protocol (or its enhancement)");
	}
	
	public static boolean checkIfFileExists(String filename){
		File file = new File(filename);
		if(file.exists())
			return true;
		System.out.println("Non existing file");
		return false;
	}

}
