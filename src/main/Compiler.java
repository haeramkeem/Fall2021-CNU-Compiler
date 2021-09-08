package main;

import fileIO.Reader;
import fileIO.Writer;

public class Compiler {
    public static void main(String[] args) {
        Reader r = new Reader("test.hf");
        char ch;
        String str = "";
        while((ch = r.next()) != '\0') {
            str += ch;
        }
        
    	Writer w = new Writer("test.c");
    	w.write(str);
    }
}