package scanner;
import htmlscanner.Automaton;

import java.util.List;

import junit.framework.TestCase;


public class AutomatonTests extends TestCase {
	public void testEmtpy() throws Exception {
		Automaton au = new Automaton();
		au.init("simpleconf.yaml");
		
		// retorna lista de errores y lista de tokens reconocidos
		ScanResult result = au.scan("");
		
	}
}
