package main;

import java.io.IOException;

public class Peer {
	private static Connection MC;
	private static Connection MDB;
	private static Connection MDR;
	private static Threads MCThread, MDBThread, MDRThread;
	private static String MCaddr;
	private static String MDBaddr;
	private static String MDRaddr;
	public Connection getMC() {
		return MC;
	}

	public Connection getMDB() {
		return MDB;
	}

	public Connection getMDR() {
		return MDR;
	}

	public Threads getMCThread() {
		return MCThread;
	}

	public Threads getMDBThread() {
		return MDBThread;
	}

	public Threads getMDRThread() {
		return MDRThread;
	}

	private static int MCport;
	private static int MDBport;
	private static int MDRport;
	
	public static void main(String args[]){
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
		
		
		try{
			MC = new Connection(MCaddr, MCport);
			MDB = new Connection(MDBaddr, MDBport);
			MDR = new Connection(MDRaddr, MDRport);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MCThread = new Threads("MC");
		MDBThread = new Threads("MDB");
		MDRThread = new Threads("MDR");
		
		MCThread.start();
		MDBThread.start();
		MDRThread.start();
		
	}
}
