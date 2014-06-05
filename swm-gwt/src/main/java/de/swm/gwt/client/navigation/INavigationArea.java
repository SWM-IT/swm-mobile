package de.swm.gwt.client.navigation;

/**
 * Definiert einen Bereich im Navigationsmenue.
 * 
 * @author Wiese.Daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public interface INavigationArea {

	/**
	 * Soll nur fuer eingeloggte Benutzer sichtbar sein.
	 * 
	 * @return true wenn es nur fuer eingeloggte Benutzer sichtbar sein soll.
	 */
	boolean isOnyLoggedInUsers();



	/**
	 * Soll nur fuer Ausgeloggte (Nicht angemeldete) Benutzer sichtbar sien.
	 * 
	 * @return true wenn es nur fuer nicht einloggte benutzer sichtbar sein soll.
	 */
	boolean isOnlyLoggedOutUsers();

}
