package main;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;

public class TestMiniC {
    public static void main(String[] args) throws IOException {
        CharStream charStream = CharStreams.fromPath(new File("src/main/test.c").toPath());
        main.MiniCLexer lexer = new main.MiniCLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        main.MiniCParser parser = new main.MiniCParser( tokens );
        ParseTree tree = parser.program();
        
        System.out.println(tree.toStringTree());
    }
}
