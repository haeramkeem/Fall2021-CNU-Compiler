package fileIO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	private  FileWriter fwriter; // 파일에 쓰기 위한 FileWriter 객체

	public Writer(String fname) {
		try {
			File file = new File("Fall2021-CNU-Compiler-termproject/output/" + fname); // 출력 파일이 위치할 폴더 경로를 prefix하고 주어진 파일 이름을 붙여 출력파일의 경로를 성정함
			if(!file.exists()) {
				file.createNewFile(); // 파일이 존재하지 않는다면 파일을 생성
			}
			this.fwriter = new FileWriter(file); // FileWriter 필드를 초기화
		} catch(IOException e) {
	        e.getStackTrace();
			System.err.println("IO exception while creating Writer object");
		}
	}
	
	public void write(String content) {
		try {
		    this.fwriter.write(content); // 주어진 문자열을 파일에 쓰기
		} catch(IOException e) {
	        e.getStackTrace();
			System.err.println("IO exception while running Write.write()");
		}
	}

	public void close() { // 파일 출력을 완료한 후에 파일을 닫기 위한 메소드
		try {
			this.fwriter.close();
		} catch(IOException e) {
	        e.getStackTrace();
			System.err.println("IO exception while running Write.close()");
		}
	}
}
