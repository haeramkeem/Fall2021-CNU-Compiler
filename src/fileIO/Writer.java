package fileIO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Writer {
	private OutputStream ostream;

	public Writer(String fname) {
		try {
		    this.ostream = new FileOutputStream("Fall2021-CNU-Compiler-termproject/src/dist/" + fname);
		} catch(IOException e) {
	        e.getStackTrace();
			
		}
	}
	
	public void write(String content) {
		try {
		    this.ostream.write(content.getBytes());
		} catch(IOException e) {
	        e.getStackTrace();
			System.out.println("IO exception while running Write.write()");
		}
	}
}
