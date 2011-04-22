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
		initINITIAL();
		
		initINT();
		initINT_FINAL();
		
		
		initESP();
		
		initIDENT();
		
		initIDENT_FINAL();
	}		
	private static void initIDENT_FINAL() {
		semanticActions.put("ident_final", new Actionable() {
			public void doAction(Automaton automaton) {
				// retornar identificador
				System.out.println(String.format("==> en pos %d hasta %d, (ID, %s)", automaton.initialPos,automaton.pos-2, automaton.getTempString()));
				
				//vaciar temporal
				automaton.temp.clear();
				// restart pos inicial temporal
				automaton.initialPos = -1;

				// volver a "initial", ejecucion epsilon
				automaton.state= "initial";
				
				// volver el puntero 1 para atras
				automaton.pos--;
			}
		});
	}
	private static void initINT_FINAL() {
		semanticActions.put("int_final", new Actionable() {
			public void doAction(Automaton automaton) {
				// retornar identificador
				System.out.println(String.format("==> en pos %d hasta %d, (INT, %s)", automaton.initialPos, automaton.pos-2, automaton.getTempString()));
				
				//vaciar temporal
				automaton.temp.clear();
				automaton.initialPos = -1;

				// volver a "initial", ejecucion epsilon
				automaton.state= "initial";
				
				// volver el puntero 1 para atras
				automaton.pos--;
			}
		});
	}
	private static void initIDENT() {
		Map<Character, String> aux;
		aux = new ImmutableMap.Builder<Character,String>()
											.put('a', "ident")
											.put('b', "ident")
											.put('c', "ident")
											.put('0', "ident")
											.put('1', "ident")
											.put(' ', "ident_final")
											.put('.', "ident_final")
											.put('$', "ident_final").build();
		transitions.put("ident", aux);
		semanticActions.put("ident", new Actionable() {
			public void doAction(Automaton automaton) {
				automaton.temp.add(automaton.index);
				if(automaton.initialPos<0){
					automaton.initialPos = automaton.pos-1;
				}
			}
		});
	}
	private static void initINT() {
		Map<Character, String> aux;
		aux = new ImmutableMap.Builder<Character,String>()
											.put('a', "int_final")
											.put('b', "int_final")
											.put('c', "int_final")
											.put('0', "int")
											.put('1', "int")
											.put(' ', "int_final")
											.put('.', "int_final")
											.put('$',"int_final").build();
		transitions.put("int", aux);
		semanticActions.put("int", new Actionable() {
			public void doAction(Automaton automaton) {
				automaton.temp.add(automaton.index);
				if(automaton.initialPos<0){
					automaton.initialPos = automaton.pos-1;
				}
			}
		});
	}
	
	
	private static void initESP() {
		semanticActions.put("esp",new Actionable() {
			public void doAction(Automaton automaton) {
				//entregar separador
				System.out.println(String.format("==> en pos %d, (SEP, )", automaton.pos-1));
				
				// ir a inicial
				automaton.state = "initial";
			}
		});
	}
	private static void initINITIAL() {
		Map<Character,String> aux = new ImmutableMap.Builder<Character,String>()
											.put('a',"ident")
											.put('b',"ident")
											.put('c',"ident")
											.put('0', "int")
											.put('1', "int")
											.put(' ',"esp")
											.put('$', "end").build();
		transitions.put("initial", aux);
	}
	static class Automaton {
		protected int initialPos;
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
		@Override
		public String toString() {
			return "Automaton [pos=" + pos + ", index='" + index + "', state="
					+ state.toUpperCase() + ", temp=\"" + temp + "\"]";
		}
		
	}
	public static void main(String[] args) {
		init();
		String input = "ab1 10. bb0b. 10.11 100101 a";
		Automaton au = new Automaton();
		
		try {
			while (au.pos <= input.length()) {
				if(au.pos < input.length()){
					au.index = input.charAt(au.pos++);
				}else{
					au.index = '$'; // end of file
					au.pos++;
				}
				String nextState = transitions.get(au.state).get(au.index);
				System.out.println(String.format("Delta(%S, '%c') = %S",
						au.state, au.index, nextState));
				if (nextState == null) {
					System.out.println(String.format("ERROR pos %d", au.pos));
				} else {
					au.state = nextState;
					if (semanticActions.containsKey(au.state)) {
						semanticActions.get(au.state).doAction(au);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("ERROR:     estado automata:\n" + au);
			e.printStackTrace();
		}
	}
}
