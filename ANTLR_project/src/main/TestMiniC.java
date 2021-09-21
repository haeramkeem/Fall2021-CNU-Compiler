package main;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;

public class TestMiniC {
    public static void main(String[] args) throws IOException {
        CharStream charStream = CharStreams.fromFileName("test.c");
        main.MiniCLexer lexer = new main.MiniCLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        main.MiniCParser parser = new main.MiniCParser( tokens );
        parser.program();
    }
}
