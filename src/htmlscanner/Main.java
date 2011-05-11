package htmlscanner;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import scanner.ScanResult;

public class Main {

	@SuppressWarnings("rawtypes")
	public static void printMap(Map map) {
		for (Object entObject : map.entrySet()) {
			Entry entry = (Entry) entObject;
			// System.out.println(String.format(
			// "key (%s): '%s' value (%s): '%s' ", entry.getKey()
			// .getClass(), entry.getKey(), entry.getValue()
			// .getClass(), entry.getValue()));
		}
	}

	public static void main(String[] args) {
		Automaton au = new Automaton();
		au.init(args[0]);
		/**/
		String input;
		try {
			input = FileUtils.readFileToString(new File(args[1]));
			ScanResult result = au.scan(input);
			System.out.println("tokens:");
			for (Token t : result.getTokens()) {
				System.out.println(t);
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
			System.exit(1);
		}
		
		/**/
	}
}
