package htmlscanner;

import java.util.Arrays;
import java.util.List;

import scanner.AutomatonTests;
import scanner.ScanResult;

public class HtmlScanTests extends AutomatonTests {
	public void testCorrectHtmlNoBreaks() {
		Automaton au = new Automaton();
		au.init("htmlconf.yaml");

		ScanResult result = au.scan("<HTML><HEAD></HEAD><BODY></BODY></HTML>");
		List<Token> tokens = Arrays.asList(
				new Token(TokenType.LESSER, null),
				new Token(TokenType.HTML_TAG_NAME, null),
				new Token(TokenType.BIGGER, null),
				new Token(TokenType.LESSER, null),
				new Token(TokenType.HEAD_TAG_NAME, null),
				new Token(TokenType.BIGGER, null),
				new Token(TokenType.LESSER, null),
				new Token(TokenType.DIVISION, null),
				new Token(TokenType.HEAD_TAG_NAME, null),
				new Token(TokenType.BIGGER, null),
				new Token(TokenType.LESSER, null),
				new Token(TokenType.BODY_TAG_NAME, null),
				new Token(TokenType.BIGGER, null),
				new Token(TokenType.LESSER, null),
				new Token(TokenType.DIVISION, null),
				new Token(TokenType.BODY_TAG_NAME, null),
				new Token(TokenType.BIGGER, null),
				new Token(TokenType.LESSER, null),
				new Token(TokenType.DIVISION, null),
				new Token(TokenType.HTML_TAG_NAME, null),
				new Token(TokenType.BIGGER, null)
				);
		assertTrue(String.format("tokens esperado: %s\ntokens resultado: %s",
				tokens, result.getTokens()), isEqual(result.getTokens(), tokens));
	}

	public void testCorrectHtmlWithBreaks() {
	}

	public void testWrongHtmlNoBreaks() {
	}

	public void testWrongHtmlWithBreaks() {
	}
}
