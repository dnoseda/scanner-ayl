package scanner;

import htmlscanner.Automaton;
import htmlscanner.Token;
import htmlscanner.TokenType;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

public class ReservedWordsTest extends AutomatonTests {

	public void testReserved() {
		setLog(true);

		ScanResult result = scan("class abstract ");
		List<Token> tokens = Arrays.asList(new Token(TokenType.CLASS, null),
				 new Token(TokenType.ABSTRACT,
						null), new Token(TokenType.SEP, null));
		assertTrue(String.format("tokens esperado: %s\ntokens resultado: %s",
				tokens, result.tokens), isEqual(result.getTokens(), tokens));
		System.out.println(result.tokens);
	}

	public void testReservedMixed() {
		setLog(true);
		Automaton au = new Automaton();
		au.init("simpleconf.yaml");
		au.setInputToScan("class abstract public extends 'a' \"hola\"");
		Token t = null;
		List<Token> result = Lists.newArrayList();
		while ((t = au.getNextToken()) != null) {
			result.add(t);
		}
		assert (isEqual(
				token(TokenType.CLASS, null).and(TokenType.SEP)
						.and(TokenType.ABSTRACT).and(TokenType.SEP)
						.and(TokenType.PUBLIC).and(TokenType.SEP)
						.and(TokenType.EXTENDS).and(TokenType.SEP)
						.and(TokenType.CARACTER, "a").and(TokenType.SEP)
						.and(TokenType.CADENA, "hola").build(), result));
	}
}
