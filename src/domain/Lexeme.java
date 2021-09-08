package domain;

public class Lexeme {
    private TokenId id;
    private String val;

    public Lexeme(TokenId id, String val) {
        this.id = id;
        this.val = val;
    }

    public TokenId getId() {
        return this.id;
    }

    public String getVal() {
        return this.val;
    }
}
