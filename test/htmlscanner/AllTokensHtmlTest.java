package htmlscanner;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	public void testAllCombinations() throws Exception{
		setLog(true);
		
		Map realconf = Yaml.loadType(new File("resources/tokens-html.yaml"), HashMap.class);
		Map<String,Object> conf = Maps.newHashMap();
		for(Object o: realconf.entrySet()){
			Entry e =(Entry) o;
			conf.put(String.valueOf(e.getKey()), e.getValue());
		}
		Map realTokens =(Map) conf.get("tokens");
		Map<String,Token> allTokens = Maps.newHashMap();
		for(Object o: realTokens.entrySet()){
			Entry e =(Entry) o;
			allTokens.put(String.valueOf(e.getKey()), (Token)e.getValue());
		}
		@SuppressWarnings("unchecked")
		List<String> seps = (List<String>) conf.get("separators");
		
		int total = allTokens.size() * allTokens.size() * seps.size();
		int testNumber = 0;
		for(Entry<String, Token> entryFirst : allTokens.entrySet()){
			for(String sep: seps){
				for(Entry<String, Token> entrySecond : allTokens.entrySet()){
					Token first = entryFirst.getValue(), second = entrySecond.getValue();
					String input = StringUtils.join(Arrays.asList(entryFirst.getKey(), sep, entrySecond.getKey()), "");
					System.out.println(String.format("[%d of %d] TESTING cadena \"%s\" y tokens %s",++testNumber, total, input,Arrays.asList(first,second)));
					assertTokens( token(first) .and(second), input,"htmlconf.yaml");
				}
			}
		}
		
	}
}
