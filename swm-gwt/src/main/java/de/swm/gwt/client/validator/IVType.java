package de.swm.gwt.client.validator;

/**
 * Definiert einen Validator uebr regulaere Expressions.
 * 
 * @author wiese.daniel
 * <br>copyright (C) 2009, SWM Services GmbH
 * 
 */
public interface IVType {

	/**
	 * Returns the regex.
	 * 
	 * @return the regex
	 */
	String getRegex();



	/**
	 * Returns the message.
	 * 
	 * @return the message
	 */
	String getMessage();



	/**
	 * Liefert true wenn die Methode <code>validateValue(String value)</code> aufgeruden werden soll.
	 * 
	 * @return true wenn zusaetzliche Valisierung notwendig ist
	 */
	boolean valueValidation();



	/**
	 * Validert den aktuellen eingabewert zusaetliche (nach inhalt, etc..).
	 * 
	 * @param value
	 *            der aktuelle wert des feldes
	 * @return null wenn korrekt ansonsten die Fehlermeldung
	 */
	String validateValue(String value);

}
