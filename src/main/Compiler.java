package main;

import controller.Parser;
import fileIO.Writer;

/**
 * 실행환경
 * 
 * Architecture : x86 64bit
 * OS : ubuntu server 20.04 LTS
 * Java : OpenJDK 11.0.11
 * GCC : 9.3.0
 */
public class Compiler {
    public static void main(String[] args) {
        Parser p = new Parser("test.hf"); // input/test.hf 파일을 읽어 파싱하는 Parser 객체 생성
    	Writer w = new Writer("test.c"); // output/test.c 파일에 쓰는 Writer 객체 생성
    	w.write(p.parse()); // 파싱한 후 쓰기
        w.close(); // 파일 닫기
    }
}