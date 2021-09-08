package fileIO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Writer {
	private OutputStream ostream;

	public Writer(String fname) {
		try {
		    this.ostream = new FileOutputStream("src/dist/" + fname);
		} catch(IOException e) {
			System.out.println("fuck");
	        e.getStackTrace();
		}
	}
	
	public void write(String content) {
		try {
		    this.ostream.write(content.getBytes());
		} catch(IOException e) {
			System.out.println("fuck1");
	        e.getStackTrace();
		}
	}
}
