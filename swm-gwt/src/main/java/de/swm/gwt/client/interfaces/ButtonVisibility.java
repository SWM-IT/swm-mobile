package de.swm.gwt.client.interfaces;

/**
 * Definiert, in welchen Faellen ein Button im Formular sichtbar ist.
 * 
 * @author Steiner.Christian
 * @author wiese.daniel <br>
 *         copyright (C) 2010, SWM Services GmbH
 * 
 */
public enum ButtonVisibility {

	/** der Button ist nur im aktiven Formular sichtbar. **/
	ENABLED_FORM,

	/** der Button ist nur im inaktiven Formular sichtbar. **/
	DISABLED_FORM,

	/** Unterkategorie: der Button ist nur im inaktiven Formular sichtbar (anzeige-modus). **/
	DISABLED_FORM_DISPLAY,

	/** Unterkategorie: der Button ist nur im inaktiven Formular sichtbar (bestaetigungs-modus). **/
	DISABLED_FORM_CONFIRM,

	/** Unterkategorie: der Button ist nur im inaktiven Formular sichtbar (nach dem Speichern, bestatigung). **/
	DISABLED_FORM_COMMITED;

	/**
	 * Prueft ob sich ein {@link ButtonVisibility} um ein inaktives Formular handelt.
	 * 
	 * @param visibility
	 *            die {@link ButtonVisibility}, die zu pruefen ist.
	 * @return true wenn es ein inaktives Formular ist.
	 */
	public static boolean isDisabled(ButtonVisibility visibility) {
		return ((visibility.equals(DISABLED_FORM)) || (visibility.equals(DISABLED_FORM_COMMITED))
				|| (visibility.equals(DISABLED_FORM_CONFIRM)) || (visibility.equals(DISABLED_FORM_DISPLAY)));
	}



	/**
	 * Prueft ob sich ein {@link ButtonVisibility} um ein aktives Formular handelt.
	 * 
	 * @param visibility
	 *            die {@link ButtonVisibility}, die zu pruefen ist.
	 * @return true wenn es ein aktives Formular ist.
	 */
	public static boolean isEnabled(ButtonVisibility visibility) {
		return visibility.equals(ENABLED_FORM);
	}
}
