package scanner;

import htmlscanner.TokenType;

public class StringCharTests extends AutomatonTests {
	public void testString() throws Exception {
		assertTokens(token(TokenType.STRING,"astring 1.23.4 123"), "\"astring 1.23.4 123\"");
	}
	
	public void testStringAndNumber() throws Exception {
		setLog(true);
		assertTokens(token(TokenType.INT,"123").and(TokenType.STRING,"astring"),"123\"astring\"");
		setLog(false);
	}
	
	public void testStringAndIdent() throws Exception {
		assertTokens(token(TokenType.INT,"123").and(TokenType.SEP,null).and(TokenType.ID,"qwe12").and(TokenType.STRING,"otro"), "123 qwe12\"otro\"");
	}
	
	public void testChar() throws Exception {
		assertTokens(token(TokenType.CHAR,"a"),"'a'");
	}
	public void testCharError() throws Exception {
		assertTokensAndError(token(TokenType.CHAR,"a"),"'ab'");		
	}
	
	public void testCharAndOthers(){
		String input = "123 12.2'a' a123\"astring\"1'g'";
		
		assertTokens(
				token(TokenType.INT, "123")
					.and(TokenType.SEP)
					.and(TokenType.REAL, "12.2")
					.and(TokenType.CHAR, "a")
					.and(TokenType.SEP)
					.and(TokenType.ID, "a123")
					.and(TokenType.STRING, "astring")
					.and(TokenType.INT, "1")
					.and(TokenType.CHAR, "g"),
				input);
	}
}
