package scanner;

import htmlscanner.Token;

import java.util.Arrays;
import java.util.List;

public class StringCharTests extends AutomatonTests {
	public void testString() throws Exception {
		ScanResult result = scan("\"astring 1.23.4 123\"");
		printResult(result);
		List<Token> expected = Arrays.asList(
				new Token("STRING","astring 1.23.4 123")
			);
		assertTrue(isEqual(expected,result.tokens));
	}
}
