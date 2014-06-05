package de.swm.gwt.client.authorization;

import com.google.gwt.user.client.ui.UIObject;

/**
 * Aktualisierung von UI-Komponenten abhängig von ihren Rechten.
 *
 * @copyright 2013 SWM Services GmbH
 */
public interface IRightsDependentUIUpdater {

	/**
	 * Registriert einen Button mit (statischen) Ausfuehrungsrechten.
	 *
	 * @param uiObject Zu authorisierendes UIObject
	 * @param accessRight Mit der Aktion verbundenes Ausfuehrungsrecht
	 */
	void register(UIObject uiObject, IAccessRight accessRight);

	/**
	 * Registriert einen Button mit (dynamischen) Ausfuehrungsrechten.
	 *
	 * Geprueft wird, ob der Benutzer die ueber RoleConfiguration definierten statischen
	 * Ausfuehrungsrechte, sowie die mit dynamicAccessRight definierten dynamischen
	 * Ausfuehrungsrechte hat.
	 *
	 * @param uiObject Zu authorisierendes UIObject
	 * @param accessRight Mit der Aktion verbundenes Ausfuehrungsrecht
	 * @param dynamicAccessRight Mit der Aktion verbundener Autorisierungs-Check
	 */
	void register(UIObject uiObject,  IAccessRight accessRight, IDynamicAccessRight dynamicAccessRight);

	/**
	 * Aktualisiert die registrierten Komponenten, so dass sie abhaengig von ihren Ausfuehrungsrechten
	 * aktiviert oder deaktiviert sind.
	 *
	 */
	void updateComponents();

}
