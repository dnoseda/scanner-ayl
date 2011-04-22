package htmlscanner;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
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
				System.out.println(String.format("==> en pos %d, (SEP, )", automaton.pos));
				
				// ir a inicial
				automaton.state = "initial";
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
		semanticActions.put("ident", new Actionable() {
			public void doAction(Automaton automaton) {
				automaton.temp.add(automaton.index);
			}
		});
		
		semanticActions.put("ident_final", new Actionable() {
			public void doAction(Automaton automaton) {
				// retornar identificador
				System.out.println(String.format("==> en pos %d, (ID, %s)", automaton.pos, automaton.getTempString()));
				
				//vaciar temporal
				automaton.temp.clear();

				// volver a "initial", ejecucion epsilon
				automaton.state= "initial";
				
				// volver el puntero 1 para atras
				automaton.pos--;
			}
		});
	}
	static class Automaton {
		int pos = 0;
		char index;
		String state = "initial";
		List<Character> temp = Lists.newArrayList();
		String getTempString(){
			StringBuilder str = new StringBuilder();
			for(Character a: temp){
				str.append(a);
			}
			return str.toString();
		}
	}
	public static void main(String[] args) {
		init();
		String input = "ab1 10. bb0b. a";
		Automaton au = new Automaton();
		while(au.pos < input.length()){
			au.index = input.charAt(au.pos++);
			String nextState = transitions.get(au.state).get(au.index);
			System.out.println(String.format("trans de %S por '%c' llega a %S", au.state, au.index, nextState));
			if(nextState == null){
				System.out.println(String.format("ERROR pos %d",au.pos));
			}else{
				au.state = nextState;
				if(semanticActions.containsKey(au.state)){
					semanticActions.get(au.state).doAction(au);
				}
			}
		}
	}
}
