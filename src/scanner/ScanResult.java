package scanner;

import htmlscanner.Token;

import java.util.List;

public class ScanResult {
	List<Token> tokens;
	List<String> errors;

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	public String toString() {
		return String.format("Tokens %s\nErrores: %s", tokens,errors);
	}
	private List<String> deltaExec;

	public List<String> getDeltaExec() {
		return deltaExec;
	}

	public void setDeltaExec(List<String> deltaExec) {
		this.deltaExec = deltaExec;
	}
}
