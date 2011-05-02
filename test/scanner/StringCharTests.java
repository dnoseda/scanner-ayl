package scanner;

public class StringCharTests extends AutomatonTests {
	public void testString() throws Exception {
		assertTokens(token("STRING","astring 1.23.4 123"), "\"astring 1.23.4 123\"");
	}
	
	public void testStringAndNumber() throws Exception {
		setLog(true);
		assertTokens(token("INT","123").and("STRING","astring"),"123\"astring\"");
		setLog(false);
	}
	
	public void testStringAndIdent() throws Exception {
		assertTokens(token("INT","123").and("SEP",null).and("ID","qwe12").and("STRING","otro"), "123 qwe12\"otro\"");
	}
	
	public void testChar() throws Exception {
		assertTokens(token("CHAR","a"),"'a'");
	}
	public void testCharError() throws Exception {
		assertTokensAndError(token("CHAR","a"),"'ab'");		
	}
	
	public void testCharAndOthers(){
		String input = "123 12.2'a' a123\"astring\"1'g'";
		
		assertTokens(
				token("INT", "123")
					.and("SEP")
					.and("REAL", "12.2")
					.and("CHAR", "a")
					.and("SEP")
					.and("ID", "a123")
					.and("STRING", "astring")
					.and("INT", "1")
					.and("CHAR", "g"),
				input);
	}
}
