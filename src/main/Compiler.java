package main;

import controller.Parser;
import fileIO.Writer;

public class Compiler {
    public static void main(String[] args) {
        Parser p = new Parser("test.hf");
    	Writer w = new Writer("test.c");
    	w.write(p.parse());
        w.close();
    }
}