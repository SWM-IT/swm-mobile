package de.swm.gwt.client;


/**
 * Allgemeines Interface fuer einen Formular-Presenter.
 *
 * @author Steiner.Christian
 * <br>copyright (C) 2010-13, SWM Services GmbH S-IP-AN-EE
 * @param <T> der Typ des Daten Transfer Objektes
 */
public interface IDisabledFormPresenter<T> {

	/**
	 * Wenn im Formular der 'Abbrechen'/'Schließen'-Button geklickt wurde.
	 */
	void handleCancel();


	/**
	 * Zeigt ein DTO in dem Formular an.
	 *
	 * @param toDisplay
	 *            das DTO.
	 */
	void displayDTO(T toDisplay);


	/**
	 * Liefert das aktuell angezeigte DTO zurueck.
	 *
	 * @return das DTO oder null.
	 */
	T getDisplayedDTO();

}
