package domain;

public class ASTNode { // Parse.parse()의 결과물로 소스 파일에 대한 AST 객체가 생성되어져 나옴
    private Lexeme self; // 자신의 노드에 대한 Lexeme 객체
    private ASTNode parent, fchild, schild; // 부모, 첫번째 자식, 두번째 자식에 대한 포인터

    // 생성자
    public ASTNode() {}
    public ASTNode(ASTNode parent) {
        this.parent = parent;
    }

    // setter
    public void setSelf(Lexeme src) {
        this.self = src;
    }

    public void setFirstChild(ASTNode src) {
        this.fchild = src;
    }

    public void setSecondChild(ASTNode src) {
        this.schild = src;
    }

    // getter
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
