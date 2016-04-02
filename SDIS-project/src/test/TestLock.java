package test;

import java.io.File;

public class TestLock {

	public static void main(String[] args) {
	File ficheiro = new File("TestLockFile.txt");
	
	ThreadFileWrite t1 = new ThreadFileWrite(ficheiro, "Sou a frase 1 caralho! \n", "T1");
	ThreadFileWrite t2 = new ThreadFileWrite(ficheiro, "Sou a frase 2 caralho! \n", "T2");
	ThreadFileWrite t3 = new ThreadFileWrite(ficheiro, "Sou a frase 3 caralho! \n", "T3");
	
	t1.start();
	t2.start();
	t3.start();
	}

}
