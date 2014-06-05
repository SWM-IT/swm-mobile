package de.swm.gwt.client.validator;

/**
 * Definiert einen regulaeren Ausdruck fuer eine Validierung.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2009, SWM Services GmbH
 * 
 */
public enum VType implements IVType {
	/** Validation expression. **/
	ALPHABET("^[a-zA-Z_]+$", "Das Eingabefeld darf nur Buchstaben enthalten."),
	/** Validation expression. **/
	ALPHANUMERIC("^[a-zA-Z0-9_]+$", "Das Eingabefeld darf nur Buchstaben oder Zahlen enthalten."),
	/** Validation expression. **/
	NUMERIC("^[+0-9]+$", "Das Eingabefeld darf nur Zahlen enthalten."),
	/** Validation expression. **/
	DECIMAL("^[+0-9]+\\,*[+0-9]*$", "Das Eingabefeld darf nur Zahlen (oder Komma bei Dezimalzahlen) enthalten."),
	/** Validation expression. **/
	EMAIL("^(\\w+)([-+.][\\w]+)*@(\\w[-\\w]*\\.){1,5}([A-Za-z]){2,4}$",
			"Das Eingabefeld muss eine Email sein wie test@swm.de."),
	/** Validation expression. **/
	JAHR("^(0?[1-9]|[12][0-9]|3[01])[.](0?[1-9]|1[012])[.](19|20)\\d\\d$",
			"Bitte geben Sie das Datum im folgenden Format TT.MM.JJJJ ein");

	private final String regex;
	private final String message;



	/**
	 * Default constructor.
	 * 
	 * @param regex
	 *            der regulaere Ausdruck
	 * @param name
	 *            der meldungstext
	 */
	VType(String regex, String name) {
		this.regex = regex;
		this.message = name;
	}



	/**
	 * Returns the regex.
	 * 
	 * @return the regex
	 */
	public String getRegex() {
		return regex;
	}



	/**
	 * Returns the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.validator.IVType#validateValue(java.lang.String)
	 */
	public String validateValue(String value) {
		return null;
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.validator.IVType#valueValidation()
	 */
	public boolean valueValidation() {
		return false;
	}
}
