package main;

import controller.SemanticAnalyser;
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
        SemanticAnalyser s = new SemanticAnalyser("test.hf"); // input/test.hf 파일을 읽어 컴파일 하는 SementicAnalyser 객체 생성
    	Writer w = new Writer("test.c"); // output/test.c 파일에 쓰는 Writer 객체 생성
    	w.write(s.analyse()); // 파싱한 후 쓰기
        w.close(); // 파일 닫기
    }
}