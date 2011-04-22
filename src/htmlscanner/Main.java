package htmlscanner;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ho.yaml.Yaml;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Main {

	static Map<String, Map<String, Object>> semanticActions = Maps.newHashMap();
	static Map<String, Map<Character, String>> transitions = Maps.newHashMap();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void init() {
		try {
			Map<String, Object> config = Yaml.loadType(new File(
					"simpleconf.yaml"), HashMap.class);
			semanticActions = (Map<String, Map<String, Object>>) config
					.get("semanticActions");
			Map aux= (Map) config
					.get("transitions");
			for(Object obj: aux.entrySet()){
				Entry ent = (Entry)obj;
				Map<Character,String> value = Maps.newHashMap();
				for(Object obj0: ((Map)ent.getValue()).entrySet()){
					Entry ent1 = (Entry) obj0;
					value.put(String.valueOf(ent1.getKey()).charAt(0), String.valueOf(ent1.getValue()));
				}
				transitions.put((String)ent.getKey(), value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class Automaton {
		protected int initialPos;
		int pos = 0;
		char index;
		String state = "initial";
		List<Character> temp = Lists.newArrayList();

		String getTempString() {
			StringBuilder str = new StringBuilder();
			for (Character a : temp) {
				str.append(a);
			}
			return str.toString();
		}

		@Override
		public String toString() {
			return "Automaton [pos=" + pos + ", index='" + index + "', state="
					+ state.toUpperCase() + ", temp=\"" + temp + "\"]";
		}

		public void execEpsilon() {
			// volver a "initial", ejecucion epsilon
			state = "initial";
		}

		public void goBackAndReset() {
			// volver el puntero 1 para atras
			pos--;
			// vaciar temporal
			temp.clear();
			// restart pos inicial temporal
			initialPos = -1;
		}

		List<Token> tokens = Lists.newArrayList();

	}

	public static void printMap(Map map) {
		for (Object entObject : map.entrySet()) {
			Entry entry = (Entry) entObject;
			System.out.println(String.format(
					"key (%s): '%s' value (%s): '%s' ", entry.getKey()
							.getClass(), entry.getKey(), entry.getValue()
							.getClass(), entry.getValue()));
		}
	}

	public static void main(String[] args) {
		init();
		/**/
		String input = "ab1 10. bb0b. 10.11 100101 a";
		System.out.println("tokens:");
		for (Token t : scan(input)) {
			System.out.println(t);
		}
		/**/
	}

	private static List<Token> scan(String input) {
		Automaton au = new Automaton();

		try {
			while (au.pos <= input.length()) {
				if (au.pos < input.length()) {
					au.index = input.charAt(au.pos++);
				} else {
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
						Map<String, Object> action = semanticActions
								.get(au.state);
						if (action.containsKey("save_value")
								&& (Boolean) action.get("save_value")) {
							System.out.println("guardo valor");
							au.temp.add(au.index);
							if (au.initialPos < 0) {
								au.initialPos = au.pos - 1;
							}
						} else { // Estado final y por lo tanto epsilon a
									// inicial
							String tokenId = (String) action.get("token_id");
							boolean reset = (Boolean) action.get("reset");
							boolean haveValue = (Boolean) action
									.get("have_value");
							Token token = new Token(tokenId,
									haveValue ? au.getTempString() : null);
							au.tokens.add(token);
							if (haveValue) {
								System.out.println(String.format(
										"==> en pos %d hasta %d, (%s, %s)",
										au.initialPos, au.pos - 2,
										token.getCode(), token.getValue()));
							} else {
								System.out.println(String.format(
										"==> en pos %d, (%s, null)",
										au.pos - 1, token.getCode()));
							}
							if (reset) {
								System.out.println("reset");
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
