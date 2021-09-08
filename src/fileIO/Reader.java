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
        	//���� ��ü ����
        	this.file = new File("src/src/" + fname);
        	//�Է� ��Ʈ�� ����
        	this.freader = new FileReader(file);
        }catch (FileNotFoundException e) {
            e.getStackTrace();
            System.out.println("File not found");
        }
	}
	
	public void close() {
		try {
			this.freader.close();
        }catch(IOException e){
            e.getStackTrace();
            System.out.println("IO excption");
        }
	}
	
	public char next() {
		int ch = -1;
		try {
			ch = this.freader.read();
        }catch(IOException e){
            e.getStackTrace();
            System.out.println("IO excption");
        }
		return ch == -1 ? '\0' : (char)ch;
	}
}
