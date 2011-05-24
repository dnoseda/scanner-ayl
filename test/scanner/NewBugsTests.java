package scanner;

import htmlscanner.TokenType;

public class NewBugsTests extends AutomatonTests {
	public void testSeveralNewlines() throws Exception {
		setLog(true);
		assertTokens(token(TokenType.ID, "lkjlkajsd").and(TokenType.IF), "lkjlkajsd\nif");
	}
}
