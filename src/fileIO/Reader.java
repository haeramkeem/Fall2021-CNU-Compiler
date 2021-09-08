package fileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	private FileReader freader;

	public Reader(String fname) {
        try{
        	File file = new File("Fall2021-CNU-Compiler-termproject/input/" + fname);
        	this.freader = new FileReader(file);
        }catch (FileNotFoundException e) {
            e.getStackTrace();
            System.err.println("File not found exception while creating Reader object");
        }
	}
	
	public void close() {
		try {
			this.freader.close();
        }catch(IOException e){
            e.getStackTrace();
            System.err.println("IO exception while running Reader.close()");
        }
	}
	
	public char next() {
		int ch = -1;
		try {
			ch = this.freader.read();
        }catch(IOException e){
            e.getStackTrace();
			System.err.println("IO exception while running Reader.next()");
        }
		return ch == -1 ? '\0' : (char)ch;
	}
}
