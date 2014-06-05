/**
 * 
 */
package de.swm.gwt.client.eventbus;

import de.swm.gwt.client.eventbus.IForwardEvent;



/**
 * Definiert adaptierbare Daten fuer Listen / Formulare.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2010, SWM Services GmbH
 * 
 */
public interface ICustomData {

	/**
	 * Ermoeglicht die Anpassung von Titlen (Formulare/Listen).
	 * 
	 * @return der neue titel.
	 */
	String title();



	/**
	 * Ermoeglicht die anpassung von Untertiteln.
	 * 
	 * @return der Untertitel.
	 */
	String subtitle();



	/**
	 * Ermoeglicht die anpassung von footern.
	 * 
	 * @return der footer.
	 */
	String footer();



	/**
	 * Kann ein benutzerdefiniertes Object an ein Formular/Liste uebermitteln.
	 * 
	 * @return das user objekt
	 */
	Object userObject();



	/**
	 * Liefert null oder ein Typsicheres Benutzerobjekt.
	 * 
	 * @param <T>
	 *            der typ
	 * @param forType
	 *            die Klasse des Typs
	 * @return das typsichere Benutzerobjekt.
	 */
	<T> T nullAndTypeSaveUserObject(Class<T> forType);



	/**
	 * Das Event was weitergeleitet werden soll.
	 * 
	 * @return das event was weitergeleitet werden soll.
	 */
	IForwardEvent forwardEvent();



	/**
	 * Ermoeglicht das setzen eines benuterobjektes.
	 * 
	 * @param toSet
	 *            das benutzerobjekt.
	 */
	void setUserObject(Object toSet);
	
}
