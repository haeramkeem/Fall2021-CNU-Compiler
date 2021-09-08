package domain;

public enum TokenId { // 토큰 식별용 enum
    TNULL, // 널 토큰
    TOPEN_BRACKET, // 열린 괄호
    TCLOSE_BRACKET, // 닫힌 괄호
    TECHO, // echo 토큰
    TLIST_DIR, // list_dir 토큰
    TDEL, // del 토큰
    TMOV, // mov 토큰
    TSHOW, // show 토큰
    TVALUE, // 값을 나타내는 토큰
    TERROR, // 에러를 나타내는 토큰
}