package scanner;

public class StringCharTests extends AutomatonTests {
	public void testString() throws Exception {
		assertTokens(token("STRING","astring 1.23.4 123"), "\"astring 1.23.4 123\"");
	}
	
	public void testStringAndNumber() throws Exception {
		assertTokens(token("INT","123").and("STRING","astring"),"123\"astring\"");
	}
}
