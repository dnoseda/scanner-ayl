package htmlscanner;

import java.util.Map;
import java.util.concurrent.Callable;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class Main {
	
	static Map<String,Actionable> semanticActions = Maps.newHashMap();
	static Map<String,Map<Character,String>> transitions = Maps.newHashMap();
	static interface Actionable{
		void doAction(Automaton automaton);
	}
	public static void init(){
		Map<Character,String> aux = new ImmutableMap.Builder<Character,String>()
											.put('a',"ident")
											.put('b',"ident")
											.put('c',"ident")
											/** /
											.put('0', "int")
											.put('1',"int")
											/**/
											.put(' ',"esp").build();
		transitions.put("initial", aux);
		
		semanticActions.put("esp",new Actionable() {
			public void doAction(Automaton automaton) {
				//entregar separador
				// volver puntero 1
				// ir a inicial
			}
		});
		
		aux = new ImmutableMap.Builder<Character,String>()
											.put('a', "ident")
											.put('b', "ident")
											.put('c', "ident")
											.put('0', "ident")
											.put('1', "ident")
											.put(' ', "ident_final")
											.put('.', "ident_final").build();
		transitions.put("ident", aux);
		
		
		semanticActions.put("ident_final", new Actionable() {
			public void doAction(Automaton automaton) {
				// retornar identificador
				// volver a "initial"
				// volver el puntero 1 para atras
			}
		});
	}
	static class Automaton {
		int pos = 0;
		char index;
		String state = "initial";
	}
	public static void main(String[] args) {
		init();
		String input = "aoiqw123, 123, ass, a";
		Automaton au = new Automaton();
		while(au.pos < input.length()){
			au.index = input.charAt(au.pos++);
			String nextState = transitions.get(au.state).get(au.index);
			if(nextState == null){
				// TODO: error
			}else{
				au.state = nextState;
				if(semanticActions.containsKey(au.state)){
					semanticActions.get(au.state).doAction(au);
				}
			}
		}
	}
}
