package scanner;

import htmlscanner.TokenType;

public class StringCharTests extends AutomatonTests {
	public void testString() throws Exception {
		assertTokens(token(TokenType.CADENA,"astring 1.23.4 123"), "\"astring 1.23.4 123\"");
	}
	
	public void testStringAndNumber() throws Exception {
		setLog(true);
		assertTokens(token(TokenType.ENTERO,"123").and(TokenType.CADENA,"astring"),"123\"astring\"");
		setLog(false);
	}
	
	public void testStringAndIdent() throws Exception {
		assertTokens(token(TokenType.ENTERO,"123").and(TokenType.SEP,null).and(TokenType.ID,"qwe12").and(TokenType.CADENA,"otro"), "123 qwe12\"otro\"");
	}
	
	public void testChar() throws Exception {
		assertTokens(token(TokenType.CARACTER,"a"),"'a'");
	}
	public void testCharError() throws Exception {
		assertTokensAndError(token(TokenType.CARACTER,"a"),"'ab'");		
	}
	
	public void testCharAndOthers(){
		String input = "123 12.2'a' a123\"astring\"1'g'";
		
		assertTokens(
				token(TokenType.ENTERO, "123")
					.and(TokenType.SEP)
					.and(TokenType.REAL, "12.2")
					.and(TokenType.CARACTER, "a")
					.and(TokenType.SEP)
					.and(TokenType.ID, "a123")
					.and(TokenType.CADENA, "astring")
					.and(TokenType.ENTERO, "1")
					.and(TokenType.CARACTER, "g"),
				input);
	}
}
