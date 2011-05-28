package scanner;

import htmlscanner.TokenType;

public class ThreeErrorsTests extends AutomatonTests {
	public void testStringNoClosedJava() throws Exception {
		setLog(true);
		String input = "\"hola mundo\n\"";
		assertTokensAndError(token(TokenType.CADENA_INCOMPLETA_ERROR,"hola mundo"), input);
	}
	
	public void test2Char()throws Exception{
		setLog(true);
		String input = "'ab'";
		assertTokensAndError(token(TokenType.CARACTER_ERROR,"a"), input);
	}
}
