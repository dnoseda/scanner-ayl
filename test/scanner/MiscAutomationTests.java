package scanner;

import htmlscanner.Token;

import java.util.Arrays;
import java.util.List;

public class MiscAutomationTests extends AutomatonTests {
	public void testEmtpy() throws Exception {
		ScanResult result = scan("");
		assertTrue(result.getTokens().isEmpty());
		assertTrue(result.getErrors().isEmpty());
	}
	
	public void testIdent() throws Exception {
		assertTokens(
				token("ID", "a10").and("SEP", null).and("ID", "bbb")
						.and("SEP", null).and("ID", "c001"), "a10 bbb c001");
	}
	
	public void testInt() throws Exception {
		ScanResult result = scan("10 00 101");
		List<Token> tokens = Arrays.asList(new Token("INT","10"), new Token("SEP",null),
				new Token("INT","00"), new Token("SEP",null),
				new Token("INT","101"));
		assertTrue(String.format("tokens esperado: %s\ntokens resultado: %s",tokens,result.tokens),isEqual(result.getTokens(),tokens));
		System.out.println(result.tokens);
	}
	
	public void testReal() throws Exception {
		ScanResult result = scan("10 0.01 101");
		List<Token> tokens = Arrays.asList(new Token("INT","10"), new Token("SEP",null),
				new Token("REAL","0.01"), new Token("SEP",null),
				new Token("INT","101"));
		assertTrue(String.format("tokens esperado: %s\ntokens resultado: %s",tokens,result.tokens),isEqual(result.getTokens(),tokens));
		System.out.println(result.tokens);
	}
	
	public void testIntAndIdent() throws Exception {
		String input = "10a";
		ScanResult result = scan(input);
		printResult(result);
		List<Token> tokens = Arrays.asList(new Token("INT","10"), new Token("ID","a"));
		assertTrue(isEqual(tokens,result.tokens));
		assertTrue(result.errors.isEmpty());
		
	}
	public void testAllLetters() throws Exception {
		ScanResult result = scan(allLetters);
		printResult(result);
		List<Token> expected =Arrays.asList(new Token("ID",allLetters));
		assertTrue(isEqual(expected ,result.tokens));
	}
	public void testMiscLettersNumbers() throws Exception {
		ScanResult result = scan("apo9231 1231assqe 2.3 FUUUj1223lAq1j2zx31");
		printResult(result);
		List<Token> expected =Arrays.asList(
					new Token("ID","apo9231"),
					new Token("SEP",null),
					new Token("INT","1231"),
					new Token("ID","assqe"),
					new Token("SEP",null),
					new Token("REAL","2.3"),
					new Token("SEP",null),
					new Token("ID","FUUUj1223lAq1j2zx31")
				);
		assertTrue(isEqual(expected ,result.tokens));
	}
}
