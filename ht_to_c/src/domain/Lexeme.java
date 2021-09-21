package domain;

public class Lexeme { // 토큰을 Lexeme 객체를 이용하여 저장
    private TokenId id; // 토큰 식별용 enum을 저장하는 필드
    private String val; // 토큰이 값을 가지고 있을 때 값을 저장하는 필드

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
