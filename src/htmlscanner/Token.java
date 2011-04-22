package htmlscanner;

public class Token {
	String code;
	String value;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Token [code=" + code + ", value=" + value + "]";
	}
	public Token(String code, String value) {
		super();
		this.code = code;
		this.value = value;
	}
	
}
