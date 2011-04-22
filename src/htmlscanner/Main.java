package htmlscanner;

import java.util.List;
import java.util.Map;

import org.ho.yaml.Yaml;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Main {
	
	static Map<String,Map<String,Object>> semanticActions = Maps.newHashMap();
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
		
		Map<String,Object> aux = new ImmutableMap.Builder<String,Object>()
			.put("token_id", "id")
			.put("have_value", true)
			.put("reset", true).build();
		semanticActions.put("ident_final", aux);
	}
	private static void initINT_FINAL() {
		Map<String,Object> aux = new ImmutableMap.Builder<String,Object>()
				.put("token_id", "int")
				.put("have_value", true)
				.put("reset", true).build();
		semanticActions.put("int_final", aux);
				
		new Actionable() {
			public void doAction(Automaton automaton) {
				// retornar identificador
				System.out.println(String.format("==> en pos %d hasta %d, (INT, %s)", automaton.initialPos, automaton.pos-2, automaton.getTempString()));
				automaton.tokens.add(new Token("INT",automaton.getTempString()));
				automaton.execEpsilon();
			}
		};
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
		Map<String, Object> auxS = new ImmutableMap.Builder<String, Object>().put("save_value",true).build();
		semanticActions.put("ident",auxS);
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
		Map<String, Object> auxS = new ImmutableMap.Builder<String, Object>().put("save_value",true).build();
		semanticActions.put("int",auxS);
	}
	
	
	private static void initESP() {
		Map<String, Object> auxS = new ImmutableMap.Builder<String, Object>()
							.put("token_id","SEP")
						    .put("have_value",false)
						    .put("reset",false)
							.build();
		semanticActions.put("esp",auxS);
		new Actionable() {
			public void doAction(Automaton automaton) {
				//entregar separador
				System.out.println(String.format("==> en pos %d, (SEP, )", automaton.pos-1));
				automaton.tokens.add(new Token("SEP",null));
				
				// ir a inicial
				automaton.state = "initial";
			}
		};
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
		public void execEpsilon(){
			// volver a "initial", ejecucion epsilon
			state= "initial";
		}
		public void goBackAndReset(){
			// volver el puntero 1 para atras
			pos--;
			//vaciar temporal
			temp.clear();
			// restart pos inicial temporal
			initialPos = -1;
		}
		
		List<Token> tokens = Lists.newArrayList(); 
		
	}
	public static void main(String[] args) {
		init();
		String input = "ab1 10. bb0b. 10.11 100101 a";
		System.out.println("tokens:");
		for(Token t : scan(input)){
			System.out.println(t);
		}
		
		System.out.println();
		System.out.println(Yaml.dump(transitions));
	}
	private static List<Token> scan(String input) {
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
						Map<String,Object> action = semanticActions.get(au.state);
						if(action.containsKey("save_value") && (Boolean)action.get("save_value")){
							au.temp.add(au.index);
							if(au.initialPos<0){
								au.initialPos = au.pos-1;
							}
						}else{ // Estado final y por lo tanto epsilon a inicial
							String tokenId = (String)action.get("token_id");
							boolean reset = (Boolean)action.get("reset");
							boolean haveValue = (Boolean)action.get("have_value");
							au.tokens.add(new Token(tokenId, haveValue ? au.getTempString() : null));
							if(reset){
								au.goBackAndReset();
							}
							au.execEpsilon();
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("ERROR:     estado automata:\n" + au);
			e.printStackTrace();
		}
		return au.tokens;
	}
}
