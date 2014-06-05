package de.swm.gwt.client.interfaces;



/**
 * Interface fuer allgemeine Actions. Koennen mit dem folgenden Adaptern verwenden werden:
 * <ul>
 * <li> MenuBarActionAdapter - Anzeige in einer MenuBar als MenuItem.</li>
 * <li> ButtonActionAdapter - Anzeige als Button in einem Formular</li>
 * </ul>
 * @param <T> der typ des Parameters
 * @author Wiese.Daniel <br>
 *         copyright (C) 2010, SWM Services GmbH S-IP-AN-EE
 */
public interface ITypedAction<T> {

	/**
	 * Fuehrt die Action aus.
	 * 
	 * @param source
	 *            ein frei konfigurierbare parameter um die Action auszufuehren.
	 */
	void execute(T source);
}
