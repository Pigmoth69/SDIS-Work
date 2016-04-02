package test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileTESTE {

	public static void main(String[] args) throws IOException {
		File newfile = new File("RandomAccess.txt");
		RandomAccessFile file = new RandomAccessFile(newfile, "rw");

		file.write("Hello World".getBytes());
		file.seek(300);
		file.write("Hello adasbfhbasfhbkasfhbksafh".getBytes());
		file.close();

	}

}
