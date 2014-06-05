/*
 * @copyright (c) 2014 SWM Services GmbH
 */

package de.swm.gwt.client.authorization;

/**
 * Verwaltet die Zuordnung von Rollen zu Rechten im Client, und prueft sie bei Aktionen des Benutzers.
 *
 * @copyright 2013 SWM Services GmbH
 */
public interface IRoleConfiguration {

	/**
	 * Autorisiert Rollen fuer ein bestimmtes Ausfuehrungsrecht.
	 *
	 * @param accessRight Ausfuehrungsrecht
	 * @param userRoles   Rollen, die fuer dieses Ausfuehrungsrecht autorisiert werden.
	 */
	void authorizeRolesForAccessRight(IAccessRight accessRight, IUserRole... userRoles);
	
	
	/**
	 * Autorisiert Rollen fuer mehrere Ausführungsrechte.
	 *
	 * @param accessRights Ausfuehrungsrechte
	 * @param userRoles   Rollen, die fuer dieses Ausfuehrungsrecht autorisiert werden.
	 */
	void authorizeRolesForAccessRights(IAccessRight[] accessRights, IUserRole[] userRoles);

	
	/**
	 * Stellt sicher, dass für alle Zugriffsrechte in accessRights eine (ggf. leere)
	 * Rollenzuweisung erfolgt ist (über authorizeRolesForAccessRight(s)).
	 * 
	 * @param accessRights
	 * @throws IllegalArgumentException, wenn für ein Zugriffsrecht keine Rollenzuweisung
	 * erfolgt ist
	 */
	void assertAllAccessRightsConfigured(IAccessRight[] accessRights);
	

	/**
	 * Prueft, ob der Benutzer fuer das gegebene Ausfuehrungsrecht autorisiert ist.
	 *
	 * @param accessRight gegebenes Ausfuehrungsrecht
	 * @return true, falls autorisiert.
	 */
	boolean isUserAuthorized(IAccessRight accessRight);

	/**
	 * Liefert die Rollen, die fuer ein Ausfuehrungsrecht autorisiert sind.
	 *
	 * @param accessRight gegebenes Ausfuehrungsrecht.
	 * @return Array der autorisierten Rollen
	 */
	public IUserRole[] getRoles(IAccessRight accessRight);

	/**
	 * Sets the security plugin - connection to an conrete security Framework.
	 *
	 * @param securityPlugin .
	 */
	void setSecurityPlugin(ISecurityPlugin securityPlugin);
}
