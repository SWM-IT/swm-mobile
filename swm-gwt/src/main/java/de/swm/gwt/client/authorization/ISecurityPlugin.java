package de.swm.gwt.client.authorization;

/**
 * Bindinng to a concrete security framework. The responsability of the security framework is to provide this
 * information.
 *
 * This interface enapulates the client side widget behavior (implemented in this de.swm.gwt.client.authorization
 * package).
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2014, Stadtwerke München GmbH
 */
public interface ISecurityPlugin {

	/**
	 * Prueft ob der aktuelle angemeldete Benutzer eine oder mehrere der uebergeben Rollen hat.
	 *
	 * @param roles
	 *            die Rollen return true wenn der User eine der Rollen hat.
	 * @return true wenn mitglied der gruppe
	 */
	 boolean isUserMemberOf(String... roles);
}
