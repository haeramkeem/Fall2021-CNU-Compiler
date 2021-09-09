package domain;

public class ASTNode {
    private Lexeme self, fchild, schild;

    public ASTNode(Lexeme self) {
        this.self = self;
        this.fchild = null;
        this.schild = null;
    }

    public void setFirstChild(Lexeme src) {
        this.fchild = src;
    }

    public void setSecondChild(Lexeme src) {
        this.schild = src;
    }

    public Lexeme getSelf() {
        return this.self;
    }

    public Lexeme getFirstChild() {
        return this.fchild;
    }

    public Lexeme getSecondChild() {
        return this.schild;
    }
}
