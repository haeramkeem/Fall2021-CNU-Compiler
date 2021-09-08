package fileIO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class Reader {
	private PushbackInputStream istream;

	public Reader(String fname) {
        try{
        	File file = new File("Fall2021-CNU-Compiler-termproject/input/" + fname);
        	this.istream = new PushbackInputStream(new BufferedInputStream(new FileInputStream(file)));
        }catch (FileNotFoundException e) {
            e.getStackTrace();
            System.err.println("File not found exception while creating Reader object");
        }
	}
	
	public void close() {
		try {
			this.istream.close();
        }catch(IOException e){
            e.getStackTrace();
            System.err.println("IO exception while running Reader.close()");
        }
	}
	
	public char next() {
		int ch = -1;
		try {
			ch = this.istream.read();
        }catch(IOException e){
            e.getStackTrace();
			System.err.println("IO exception while running Reader.next()");
        }
		return ch == -1 ? '\0' : (char)ch;
	}

	public void ungetc(char ch) {
		char[] temp = {ch};
		try {
			this.istream.unread(new String(temp).getBytes());
		} catch(IOException e) {
            e.getStackTrace();
			System.err.println("IO exception while running Reader.ungetc()");
		}
	}
}
