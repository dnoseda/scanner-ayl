package scanner;

import htmlscanner.Token;
import htmlscanner.TokenType;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;


public class AllTokensJavaTest extends AutomatonTests {
	public void testAllTokens() throws Exception {
		String input = "iuo123\n" +"123\n" +"0.23\n" +"'s'\n" +
		"\"ajhskjhd\"\n" +"public\n" +"class\n" +",\n" +
		";\n" +"int\n" +"char\n" +"float\n" +
		"double\n" +"[\n" +"]\n" +"{\n" +
		"}\n" +"if\n" +"(\n" +")\n" +
		"else\n" +"while\n" +"return\n" +"+\n" +
		"=\n" +"&&\n" +"==\n" +"<\n" +
		">\n" +"-\n" +"∗\n" +"/\n" +
		"++\n" +"new\n";
		
		System.out.println(StringUtils.join(scan(input).tokens,"\n"));
		System.out.println("INPUT:\n"+input);
	}
	
	public void testIntReal() throws Exception {
		setLog(true);
		assertTokens(token(TokenType.ENTERO,"123").and(TokenType.REAL,"0.23"), "123\n0.23");
		
	}
}
