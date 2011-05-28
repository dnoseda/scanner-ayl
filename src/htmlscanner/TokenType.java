package htmlscanner;

public enum TokenType {
	ENTERO(1), ID(2), CADENA(3), REAL(4), CARACTER(5), SEP(6), ERROR(7), ABSTRACT(
			8), BOOLEAN(9), BREAK(10), BYTE(11), CASE(12), CATCH(13), CHAR(14), CLASS(
			15), CONST(16), CONTINUE(17), DEFAULT(18), DO(19), DOUBLE(20), ELSE(
			21), EXTENDS(22), FALSE(23), FINAL(24), FINALLY(25), FLOAT(26), FOR(
			27), GOTO(28), IF(29), IMPLEMENTS(30), IMPORT(31), INSTANCEOF(32), INT(
			33), INTERFACE(34), LONG(35), NATIVE(36), NEW(37), NULL(38), PACKAGE(
			39), PRIVATE(40), PROTECTED(41), PUBLIC(42), RETURN(43), SHORT(44), STATIC(
			45), STRICTFP(46), SUPER(47), SWITCH(48), SYNCHRONIZED(49), THIS(50), THROW(
			51), THROWS(52), TRANSIENT(53), TRUE(54), TRY(55), VOID(56), VOLATILE(
			57), WHILE(58),
			DIVISION(59),
			MULTIPLICATION(60),
			SUM(61),
			DIFFERENCE(62),
			ASSIGNATION(63),
			POWER(64),
			LOGIC_AND(65),
			LOGIC_OR(66),
			O_P(67),
			C_P(68),
			O_BRACKET(69),
			C_BRACKET(70),
			O_BRACE(71),
			C_BRACE(72),
			PCT(73),
			BIGGER(74),
			LESSER(75),
			POSTINCREMENT(76), 
			SUMASSIGN(77), 
			HTML_TAG_NAME(78), 
			HEAD_TAG_NAME(79), 
			BODY_TAG_NAME(80),
			CDATA(81),
			ASCII(82),
			QUOTED_CDATA(83),
			QUOTED_ASCII(84),
			A_TAG_NAME(85),
			HREF(86), PROFILE(87),COMMA(88),SEMICOLON(89),EQUAL(90)
			,TITLE_TAG_NAME(91),P_TAG_NAME(92),TITLE(93), CHAR_ERROR(94), CADENA_INCOMPLETA_ERROR(95), CARACTER_ERROR(96);

	private int code;

	public int getIntCode() {
		return code;
	}

	TokenType(int code) {
		this.code = code;
	}
}
