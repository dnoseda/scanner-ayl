package scanner;

import htmlscanner.Token;

import java.util.Arrays;
import java.util.List;

import org.ho.yaml.Yaml;

import com.google.common.collect.ImmutableMap;

public class StringCharTests extends AutomatonTests {
	public void testString() throws Exception {
		ScanResult result = scan("\"astring 1.23.4 123\"");
		printResult(result);
		List<Token> expected = Arrays.asList(
				new Token("STRING","astring 1.23.4 123")
			);
		System.out.println(Yaml.dump(ImmutableMap.of("\"","string")));
		assertTrue(isEqual(expected,result.tokens));
	}
}
