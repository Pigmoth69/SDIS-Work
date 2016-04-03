package test;

import java.io.File;

import database.Info;

public class TestLock {

	public static void main(String[] args) {
	File ficheiro = new File("TestLockFile.txt");
	
	
	for(int i = 1 ; i <= 50; i++){
		Info info = new Info();
		ThreadFileWrite t1 = new ThreadFileWrite(ficheiro, info, "T"+i);
		t1.start();
	}
	

	}

}
