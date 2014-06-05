/*
 * @copyright (c) 2014 SWM Services GmbH
 */

package de.swm.gwt.client.authorization;

/**
 * Interface fuer Benutzerrollen.
 *
 * @copyright 2013 SWM Services GmbH
 */

public interface IUserRole {

	/**
	 * Liefert die ActiveDirectory-Gruppe, mit der diese Rolle verwaltet wird.
	 * @return Bezeichnung der AD-Gruppe
	 */
	String getGroup();
}
