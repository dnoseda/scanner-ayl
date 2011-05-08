package scanner;

import java.util.List;

import org.ho.yaml.Yaml;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import htmlscanner.Automaton;
import htmlscanner.Token;
import htmlscanner.TokenType;

public class ForDemandTests extends AutomatonTests {
	public void testname() throws Exception {
		Automaton au = new Automaton();
		au.init("simpleconf.yaml");
		au.setInputToScan("12.23 12314 i78sidu 'a' \"hola\"");
		Token t = null;
		List<Token> result = Lists.newArrayList(); 
		while((t=au.getNextToken())!=null){
			result.add(t);
		}
		assert(isEqual(token(TokenType.REAL,"12.23")
						.and(TokenType.SEP)
						.and(TokenType.ENTERO,"12314")
						.and(TokenType.SEP)
						.and(TokenType.ID)
						.and(TokenType.SEP)
						.and(TokenType.CARACTER,"a")
						.and(TokenType.SEP)
						.and(TokenType.CADENA,"hola").build(), result));
	}
	public static void main(String[] args) {
		System.out.println("\""+Yaml.dump(ImmutableMap.of("all_chars","!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\n 	"))+"\"");
	}
}
