package htmlscanner;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.ho.yaml.Yaml;

import com.google.common.collect.Maps;

import scanner.AutomatonTests;

public class AllTokensHtmlTest extends AutomatonTests {
	public void testAllTokens() throws Exception {
		String input = "<\n" + 
				"HTML\n" + 
				">\n" + 
				"<\n" +
				"/\n" + 
				"HEAD\n" + 
				"profile\n" + 
				"=\n" + 
				"TITLE\n" + 
				"BODY\n" + 
				"P\n" + 
				"A\n" + 
				"href\n" + 
				"class\n" + 
				"\"123 . aqwdqw\"\n" + 
				"title\n" + 
				"<!-- lkajslkdjoqwieu\n" + 
				"-->\n" +
				"qwiueqwiy12iuo21oiu31221..w";
		
		Iterator<Token> tokens = scan(input,"htmlconf.yaml").getTokens().iterator();
		Map<String, Token> toTest = Maps.newHashMap();
		for (String s : input.split("\n")) {
			Token t = null;
			if (tokens.hasNext()) {
				t = tokens.next();
			}
			toTest.put(s, t);
		}
		System.out.println(Yaml.dump(toTest));
	}
	
	public void testAssign() throws Exception {
		setLog(true);
		assertTokens(token(TokenType.EQUAL,null), "=","htmlconf.yaml");
	}
	public void testTagComment() throws Exception {
		setLog(true);
		assertTokens(token(TokenType.TITLE,null), "title\n" + 
				"<!-- lkajslkdjoqwieu\n" + 
				"-->\n","htmlconf.yaml");
	}
	public void testIdent() throws Exception {
		setLog(true);
		assertTokens(token(TokenType.CDATA,"Shaldkjahsldqiwuey1112.-"), "Shaldkjahsldqiwuey1112.-","htmlconf.yaml");
	}
}
