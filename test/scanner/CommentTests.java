package scanner;

import org.ho.yaml.Yaml;

import com.google.common.collect.ImmutableMap;

import htmlscanner.TokenType;

public class CommentTests extends AutomatonTests {
	public void testCommentInLine() throws Exception {
		String input = "// cualquier cosa\n" +
				"i12341";
		assertTokens(token(TokenType.ID,"i12341"), input);
	}
	
	public void testLiteralAndComment() throws Exception {
		String input = "9876.004// cualquier cosa";
		assertTokens(token(TokenType.REAL,"9876.004"), input);
	}
	
	public void testMultilineComment() throws Exception {
		setLog(true);
		assertTokens(token(TokenType.CHAR,"a").and(TokenType.STRING,"un string"),
				"'a'/** lkjadhqlwkjqhelk q2p34534hqlkewh lkjrhasfdoiasdfasd\nj786123874 91b7978dafsdncsaodisudybvfisndc **/\"un string\"");
	}
	
	public static void main(String[] args) {
		System.out.println(Yaml.dump(ImmutableMap.of("nl","\n","a","*","b","/")));
	}
}
