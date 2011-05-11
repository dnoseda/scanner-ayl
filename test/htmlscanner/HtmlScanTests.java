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
		printResult(result);
		assertTrue(String.format("tokens esperado: %s\ntokens resultado: %s",
				tokens, result.getTokens()), isEqual(result.getTokens(), tokens));
	}

	public void testCorrectHtmlWithBreaks() {
		Automaton au = new Automaton();
		au.init("htmlconf.yaml");

		ScanResult result = au.scan("<HTML>\n<HEAD></HEAD>\n<BODY>\nAlgo de texto. \"Algo de quote text\"\n</BODY>\n</HTML>");
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
		new Token(TokenType.CDATA, "Algo"),
		new Token(TokenType.CDATA, "de"),
		new Token(TokenType.CDATA, "texto."),
		new Token(TokenType.QUOTED_CDATA, "Algo de quote text"),
		new Token(TokenType.LESSER, null),
		new Token(TokenType.DIVISION, null),
		new Token(TokenType.BODY_TAG_NAME, null),
		new Token(TokenType.BIGGER, null),
		new Token(TokenType.LESSER, null),
		new Token(TokenType.DIVISION, null),
		new Token(TokenType.HTML_TAG_NAME, null),
		new Token(TokenType.BIGGER, null)
		
		);
		printResult(result);
	}

	public void testCorrectHtmlComplexTags() {
		Automaton au = new Automaton();
		au.init("htmlconf.yaml");

		ScanResult result = au.scan("<HTML><HEAD profile = frula></HEAD><BODY class = \"2CDATA\"><A href = \"refref\"></A></BODY></HTML>");
		List<Token> tokens = Arrays.asList(
				new Token(TokenType.LESSER, null),
				new Token(TokenType.HTML_TAG_NAME, null),
				new Token(TokenType.BIGGER, null),
				new Token(TokenType.LESSER, null),
				new Token(TokenType.HEAD_TAG_NAME, null),
				
				new Token(TokenType.PROFILE, null),
				new Token(TokenType.ASCII, "="),
				new Token(TokenType.CDATA, "frula"),
				
				new Token(TokenType.BIGGER, null),
				new Token(TokenType.LESSER, null),
				new Token(TokenType.DIVISION, null),
				new Token(TokenType.HEAD_TAG_NAME, null),
				new Token(TokenType.BIGGER, null),
				new Token(TokenType.LESSER, null),
				new Token(TokenType.BODY_TAG_NAME, null),
				new Token(TokenType.CLASS, null),
				new Token(TokenType.ASCII, "="),
				new Token(TokenType.QUOTED_ASCII, "2CDATA"),
				new Token(TokenType.BIGGER, null),
				
				new Token(TokenType.LESSER, null),
				new Token(TokenType.A_TAG_NAME, null),
				new Token(TokenType.HREF, null),
				new Token(TokenType.ASCII, "="),
				new Token(TokenType.QUOTED_CDATA, "refref"),
				new Token(TokenType.BIGGER, null),
				
				new Token(TokenType.LESSER, null),
				new Token(TokenType.DIVISION, null),
				new Token(TokenType.A_TAG_NAME, null),
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
		printResult(result);
		assertTrue(String.format("tokens esperado: %s\ntokens resultado: %s",
				tokens, result.getTokens()), isEqual(result.getTokens(), tokens));
	}
	
	public void testHtmlWithComments(){
		Automaton au = new Automaton();
		au.init("htmlconf.yaml");

		ScanResult result = au.scan("<HTML><HEAD></HEAD><!-- esto es un comment y no habria que darle bola 1984 //--><BODY></BODY></HTML>");
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
		printResult(result);
		assertTrue(String.format("tokens esperado: %s\ntokens resultado: %s",
				tokens, result.getTokens()), isEqual(result.getTokens(), tokens));
	}
	
}
