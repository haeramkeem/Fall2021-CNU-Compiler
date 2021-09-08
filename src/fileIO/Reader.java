package fileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	private File file;
	private FileReader freader;

	public Reader(String fname) {
        try{
        	this.file = new File("src/src/" + fname);
        	this.freader = new FileReader(file);
        }catch (FileNotFoundException e) {
            e.getStackTrace();
            System.out.println("File not found exception while creating Reader object");
        }
	}
	
	public void close() {
		try {
			this.freader.close();
        }catch(IOException e){
            e.getStackTrace();
            System.out.println("IO exception while running Reader.close()");
        }
	}
	
	public char next() {
		int ch = -1;
		try {
			ch = this.freader.read();
        }catch(IOException e){
            e.getStackTrace();
			System.out.println("IO exception while running Reader.next()");
        }
		return ch == -1 ? '\0' : (char)ch;
	}
}
