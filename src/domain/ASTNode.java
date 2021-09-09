package domain;

public class ASTNode {
    private Lexeme self;
    private ASTNode parent, fchild, schild;

    public ASTNode() {}
    public ASTNode(ASTNode parent) {
        this.parent = parent;
    }

    public void setSelf(Lexeme src) {
        this.self = src;
    }

    public void setParent(ASTNode src) {
        this.parent = src;
    }

    public void setFirstChild(ASTNode src) {
        this.fchild = src;
    }

    public void setSecondChild(ASTNode src) {
        this.schild = src;
    }

    public Lexeme getSelf() {
        return this.self;
    }

    public ASTNode getParent() {
        return this.parent;
    }

    public ASTNode getFirstChild() {
        return this.fchild;
    }

    public ASTNode getSecondChild() {
        return this.schild;
    }
}
