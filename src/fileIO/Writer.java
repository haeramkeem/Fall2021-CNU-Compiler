package fileIO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	private  FileWriter fwriter;

	public Writer(String fname) {
		try {
			File file = new File("Fall2021-CNU-Compiler-termproject/output/" + fname);
			if(!file.exists()) {
				file.createNewFile();
			}
			this.fwriter = new FileWriter(file);
		} catch(IOException e) {
	        e.getStackTrace();
			System.err.println("IO exception while creating Writer object");
		}
	}
	
	public void write(String content) {
		try {
		    this.fwriter.write(content);
		} catch(IOException e) {
	        e.getStackTrace();
			System.err.println("IO exception while running Write.write()");
		}
	}

	public void close() {
		try {
			this.fwriter.close();
		} catch(IOException e) {
	        e.getStackTrace();
			System.err.println("IO exception while running Write.close()");
		}
	}
}
