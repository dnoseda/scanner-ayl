package htmlscanner;

public enum TokenType {
	INT(1), ID(2), STRING(3),
	REAL(4), CHAR(5), SEP(6),
	ERROR(7);
	private int code;
	public int getIntCode() {
		return code;
	}
	TokenType(int code){
		this.code = code;
	}
}
