package de.swm.gwt.client.authorization.impl;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.inject.Inject;

import de.swm.gwt.client.authorization.IAccessRight;
import de.swm.gwt.client.authorization.IDynamicAccessRight;
import de.swm.gwt.client.authorization.IRightsDependentUIUpdater;
import de.swm.gwt.client.authorization.IRoleConfiguration;

/**
 * Aktualisierung von UI-Komponenten abhängig von ihren Rechten.
 *
 * @copyright 2013 SWM Services GmbH
 */
public class RightsDependentUIUpdater implements IRightsDependentUIUpdater {

	@Inject
	private IRoleConfiguration roleConfiguration;
	
	private String unauthorizedAttribute;

	private Map<UIObject, IAccessRight> componentsToAccessRights = new HashMap<UIObject, IAccessRight>();
	private Map<UIObject, IDynamicAccessRight> componentsToDynamicAccessRights
		= new HashMap<UIObject, IDynamicAccessRight>();


	/**
	 * Initialisiert die Rechte.
	 *
	 */
	public RightsDependentUIUpdater() {
	}
	
	
	/**
	 * Setzt das Attribut, das einem UI-Element in der Implementierung von
	 * {@link #updateComponent(UIObject, boolean)} zugefuegt wird, falls der Benutzer
	 * fuer dieses Element kein Zugriffsrecht besitzt.
	 * 
	 * @param unauthorizedAttribute
	 */
	public void setUnauthorizedAttribute(String unauthorizedAttribute) {
		this.unauthorizedAttribute = unauthorizedAttribute;
	}

	@Override
	public void register(UIObject uiobject, IAccessRight accessRight) {
		componentsToAccessRights.put(uiobject, accessRight);
	}

	@Override
	public void register(UIObject uiobject, IAccessRight accessRight, IDynamicAccessRight authorizationCheck) {
		register(uiobject, accessRight);
		componentsToDynamicAccessRights.put(uiobject, authorizationCheck);
	}

	@Override
	public void updateComponents() {
		for (Map.Entry<UIObject, IAccessRight> e : componentsToAccessRights.entrySet()) {
			UIObject uiobject = e.getKey();

			IAccessRight executionRight = componentsToAccessRights.get(uiobject);
			if (executionRight == null) {
				throw new IllegalArgumentException("Kein Ausführungsrecht für das UiObject "
						+ uiobject.toString() + " konfiguriert.");
			}


			boolean staticCheck = roleConfiguration.isUserAuthorized(executionRight);

			IDynamicAccessRight authorizationCheck = componentsToDynamicAccessRights.get(uiobject);
			boolean dynamicCheck = authorizationCheck == null
					|| authorizationCheck.isAuthorized();
			
			updateComponent(uiobject, staticCheck && dynamicCheck);
		}
	}
	
	/**
	 * Aktualisiert die Anzeige der Komponente abhängig von der Berechtigung.
	 * Standardverhalten:
	 * falls unauthorizedAttribute im RightsDependentUiUpdater gesetzt ist, wird dieses dem UI-Objekt zugefügt,
	 *    falls der Benutzer nicht berechtigt ist.
	 * ansonsten: Komponente wird aktiviert/deaktiviert, falls FocusWidget.
	 * 
	 * Kann überschrieben werden (z.B. Ausblenden über setVisible).
	 * 
	 * @param uiobject UI-Objekt
	 * @param authorized true, wenn Benutzer berechtigt ist, Aktionen auf diesem UI-Objekt auszuführen; false sonst
	 */
	protected void updateComponent(UIObject uiobject, boolean authorized) {
		if (unauthorizedAttribute != null) {
			uiobject.getElement().removeAttribute(unauthorizedAttribute);
			if (!authorized) {
				uiobject.getElement().setAttribute(unauthorizedAttribute, "");
			}
		} else if (uiobject instanceof FocusWidget) {
			((FocusWidget)uiobject).setEnabled(authorized);
		}
	}

}
