/*
 * @copyright (c) 2014 SWM Services GmbH
 */

package de.swm.gwt.client.authorization.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;

import de.swm.gwt.client.authorization.IAccessRight;
import de.swm.gwt.client.authorization.IRoleConfiguration;
import de.swm.gwt.client.authorization.ISecurityPlugin;
import de.swm.gwt.client.authorization.IUserRole;

/**
 * Verwaltet die Zuordnung von Rollen zu Rechten im Client, und prueft sie bei Aktionen des Benutzers.
 *
 * @copyright 2013 SWM Services GmbH
 */
public class RoleConfiguration implements IRoleConfiguration {

	private Map<IAccessRight, IUserRole[]> accessRightsToRoles = new HashMap<IAccessRight, IUserRole[]>();
	private ISecurityPlugin securityPlugin;

	/**
	 * Standard-Constructor.
	 */
	public RoleConfiguration() {
	}

	@Override
	public void setSecurityPlugin(ISecurityPlugin securityPlugin) {
		this.securityPlugin = securityPlugin;
	}

	@Override
	public void authorizeRolesForAccessRight(IAccessRight accessRight, IUserRole... userRoles) {
		accessRightsToRoles.put(accessRight, userRoles);
	}

	@Override
	public void authorizeRolesForAccessRights(IAccessRight[] accessRights,
			IUserRole[] userRoles) {
		
		for (IAccessRight accessRight : accessRights) {
			authorizeRolesForAccessRight(accessRight, userRoles);
		}
		
	}

	@Override
	public void assertAllAccessRightsConfigured(IAccessRight[] accessRights) {
		for (IAccessRight r : accessRights) {
			if (getRoles(r) == null) {
				throw new IllegalArgumentException("Keine Rollen für das Ausführungsrecht "
						+ r.toString() + " konfiguriert.");
			}
		}
	}

	/**
	 * Prueft, ob der Benutzer fuer eine Methode mit der angehaengten Liste
	 * von Rollen autorisiert ist.
	 *
	 * @param roles Rollen, die der Benutzer haben muss
	 * @return true, falls autorisiert.
	 */
	private boolean isUserMemberOf(IUserRole... roles) {

		List<String> groups = new ArrayList<String>();
		for (IUserRole role : roles) {
			groups.add(role.getGroup());
		}
		if (securityPlugin == null) {
			Window.alert("Please define a concrete Security framework like SWM-Accesscontrol!");
			throw new RuntimeException("Please define a concrete Security framework like SWM-Accesscontrol!");
		}
		return securityPlugin.isUserMemberOf((String[]) groups.toArray(new String[groups.size()]));
	}

	@Override
	public boolean isUserAuthorized(IAccessRight accessRight) {
		IUserRole[] roles = getRoles(accessRight);
		return (roles != null) && isUserMemberOf(roles);
	}

	/**
	 * Ermittelt die Rollen, die fuer das Ausfuehrungsrecht autorisiert sind.
	 *
	 * @param accessRight das gegebene Ausfuehrungsrecht.
	 * @return Array der autorisierten Rollen.
	 */
	public IUserRole[] getRoles(IAccessRight accessRight) {
		IUserRole[] userRoles = accessRightsToRoles.get(accessRight);
		return userRoles;
	}
}