package de.swm.gwt.client.authorization;

/**
 * Interface fuer die beliebige Rechtepruefungen.
 *
 *  @copyright 2013 SWM Services GmbH
 */
public interface IDynamicAccessRight {

	/**
	 * Prueft, ob eine Aktion autorisiert ist.
	 *
	 * Ueblicherweise als anonyme (Funktions-)Klasse verwendet.
	 *
	 * @return true, falls authorisiert.
	 */
	boolean isAuthorized();

}
