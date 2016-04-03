package comunication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;

import main.Client;
import database.Info;
import database.Serial;

public class Peer implements PeerInterface{
	private static Connection MC, MDB, MDR;
	private static ChannelThreads MCThread, MDBThread, MDRThread;
	private static String MCaddr, MDBaddr, MDRaddr;
	private static String senderId;
	private Info info;
	private Serial serial;
	private MessageSubject subj;
	
	
	public Serial getSerial() {
		return serial;
	}

	public void setSerial(Serial serial) {
		this.serial = serial;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public Connection getMC() {
		return MC;
	}

	public Connection getMDB() {
		return MDB;
	}

	public Connection getMDR() {
		return MDR;
	}

	public ChannelThreads getMCThread() {
		return MCThread;
	}

	public ChannelThreads getMDBThread() {
		return MDBThread;
	}

	public ChannelThreads getMDRThread() {
		return MDRThread;
	}

	private static int MCport;
	private static int MDBport;
	private static int MDRport;
	
	public Peer(String args[]){
		if (args.length != 7){
			System.out.println("Invalid number of arguments in ip file");
			return;
		}
		MCaddr = args[0];
		MDBaddr = args[2];
		MDRaddr = args[4];
		MCport = Integer.parseInt(args[1]);
		MDBport = Integer.parseInt(args[3]);
		MDRport = Integer.parseInt(args[5]);
		
			
		senderId = args[6];
		
		
		try{
			//System.out.println("Iniciei as connections!!");
			MC = new Connection(MCaddr, MCport);
			MDB = new Connection(MDBaddr, MDBport);
			MDR = new Connection(MDRaddr, MDRport);
			System.out.println("Peer connected");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		info = new Info();
		serial = new Serial(info);
		boolean loaded = serial.Load("database/info.db");
		if (loaded){
			info = serial.getInfo();
		}
		
		subj = new MessageSubject();
		
		
		System.out.println("Iniciei as threads do PEER!!");
		MCThread = new ChannelThreads(this, "MC");
		MDBThread = new ChannelThreads(this, "MDB");
		MDRThread = new ChannelThreads(this, "MDR");
		System.out.println("Threads do PEER já iniciadas!!");
		
		//MCThread.start();
		//MDBThread.start();
		//MDRThread.start();
		
	}

	public MessageSubject getSubj() {
		return subj;
	}

	public void setSubj(MessageSubject subj) {
		this.subj = subj;
	}
	
    public static void main(String[] args) {

        if(args.length != 7){
            System.err.println("Usage: <mcIP> <mcPort> <mdbIP> <mdbPort> <mdrIP> <mdrPort> <Peer ID>");
            System.exit(-1);
        }
        
        int peerID = Integer.parseInt(args[6]);
        senderId = args[6];
        Peer peer = new Peer(args);
        try {
            PeerInterface stub = (PeerInterface) UnicastRemoteObject.exportObject(peer, peerID);
            Registry registry = LocateRegistry.createRegistry(peerID);
            registry.rebind("Peer",stub);
        } catch (RemoteException e) {
            System.err.println("Cannot export RMI Object");
            System.exit(-1);
        }

        MCThread.start();
		MDBThread.start();
		MDRThread.start();
        
    }

	@Override
	public void putFile(String filename, int repDegree) throws RemoteException {
		String args[] = {this.senderId, "BACKUP", filename, ""+repDegree};
		
		try {
			Client c = new Client(this, args);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	@Override
	public void restoreFile(String filename) throws RemoteException {
		String args[] = {this.senderId, "RESTORE", filename};
		
		try {
			Client c = new Client(this, args);
		} catch (NumberFormatException | NoSuchAlgorithmException | IOException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteFile(String filename) throws RemoteException {
		String args[] = {this.senderId, "DELETE", filename};
		
		try {
			Client c = new Client(this, args);
		} catch (NumberFormatException | NoSuchAlgorithmException | IOException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void reclaimSpace(int space) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
