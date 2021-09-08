package fileIO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class Reader {
	private PushbackInputStream istream; // C 언어에서의 ungetc 기능을 사용하기 위해 다시 포인터를 되돌리는 기능을 제공하는 PushbackInputStream 클래스를 사용

	public Reader(String fname) {
        try{
        	File file = new File("Fall2021-CNU-Compiler-termproject/input/" + fname); // 프로젝트 최상단으로부터 입력 파일이 담길 폴더까지 prefix한 후 입력받은 파일의 이름을 붙여 File 객체를 생성
        	this.istream = new PushbackInputStream(new BufferedInputStream(new FileInputStream(file))); // File객체를 이용해 PushbackInputStream 필드를 초기화
        }catch (FileNotFoundException e) {
            e.getStackTrace();
            System.err.println("File not found exception while creating Reader object");
        }
	}
	
	public void close() { // InputStream을 닫는 메소드
		try {
			this.istream.close();
        }catch(IOException e){
            e.getStackTrace();
            System.err.println("IO exception while running Reader.close()");
        }
	}
	
	public char next() { // 다음 문자를 읽어들이는 메소드
		int ch = -1;
		try {
			ch = this.istream.read();
        }catch(IOException e){
            e.getStackTrace();
			System.err.println("IO exception while running Reader.next()");
        }
		return ch == -1 ? '\0' : (char)ch; // 더이상 읽을 값이 없으면 널문자를 반환하도록 함
	}

	public void ungetc(char ch) {
		char[] temp = {ch}; // PushbackInputStream.unread() 메소드는 바이트 배열을 받기 때문에 우선 입력받은 문자를 배열화 함
		try {
			this.istream.unread(new String(temp).getBytes()); // 문자 배열을 String 객체로 변환한 후 바이트 배옇로 변환하여 인자로 넣어줌
		} catch(IOException e) {
            e.getStackTrace();
			System.err.println("IO exception while running Reader.ungetc()");
		}
	}
}
