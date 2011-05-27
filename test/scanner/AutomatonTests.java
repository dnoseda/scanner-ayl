package scanner;

import htmlscanner.Automaton;
import htmlscanner.Token;
import htmlscanner.TokenType;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import junit.framework.TestCase;

public abstract class AutomatonTests extends TestCase {

	protected void printResult(ScanResult result) {
		System.out.println("Tokens: " + result.tokens);
		System.out.println("Errors: " + result.errors);
		if(log){
			System.out.println("Delta:\n\t" + StringUtils.join(result.getDeltaExec(),"\n\t"));
		}
	}
	boolean log= false;
	protected void setLog(boolean value){
		log = value;
	}

	protected static String allLetters = "abcdefghijklmnopqrstuvwxyz";
	static {
		allLetters += allLetters.toUpperCase();
	}

	public ScanResult scan(String input,String confFile) {
		Automaton au = new Automaton();
		au.init(confFile);

		ScanResult result = au.scan(input);
		return result;
	}
	public ScanResult scan(String input) {
		return scan(input,"simpleconf.yaml");
	}

	public static boolean isEqual(List<Token> tokens, List<Token> tokens2) {
		Preconditions.checkNotNull(tokens);
		Preconditions.checkNotNull(tokens2);

		if (tokens.isEmpty() && tokens2.isEmpty()) {
			return true;
		}

		if (tokens.size() != tokens2.size()) {
			return false;
		}

		for (int i = 0; i < tokens.size(); i++) {
			if (!(tokens.get(i).equals(tokens2.get(i)))) {
				return false;
			}
		}
		return true;
	}

	protected void assertTokens(TokenBuilder expected, String input){
		assertTokens(expected,input,"simpleconf.yaml");
	}
	protected void assertTokens(TokenBuilder expected, String input, String fileName) {
		ScanResult result = scan(input,fileName);
		printResult(result);
		assertTrue(String.format("expected: %s and current %s",
				expected.build(), result.tokens),
				isEqual(expected.build(), result.tokens));
		assertTrue(result.errors.isEmpty());
	}
	
	protected void assertTokensAndError(TokenBuilder expected, String input) {
		ScanResult result = scan(input);
		printResult(result);
		assertTrue(String.format("expected: %s and current %s",
				expected.build(), result.tokens),
				isEqual(expected.build(), result.tokens));
		assertFalse(result.errors.isEmpty());
	}

	protected TokenBuilder token(Token token){
		return token(token.getCode(),token.getValue());
	}
	protected TokenBuilder token(TokenType key, String value) {
		return new TokenBuilder(key, value);
	}

	static class TokenBuilder {
		List<Token> tokens = Lists.newArrayList();

		TokenBuilder(TokenType key, String value) {
			tokens.add(new Token(key, value));
		}

		TokenBuilder and(TokenType key, String value) {
			tokens.add(new Token(key, value));
			return this;
		}
		TokenBuilder and(Token token){
			return and(token.getCode(),token.getValue());
		}
		TokenBuilder and(TokenType key){
			return and(key,null);
		}

		List<Token> build() {
			return tokens;
		}
	}
}
