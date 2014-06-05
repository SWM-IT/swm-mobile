package de.swm.gwt.client;


/**
 * Allgemeines Interface fuer Plain GWT-Formulare.
 *
 * @param <T> Das DTO des Formulars.
 * copyright (C) 20xx, SWM Services GmbH S-IP-AN-EE
 */
public interface IForm<T> extends IBaseForm<T> {

	/**
	 * Setzt den Presenter des Formulars.
	 *
	 * @param presenter der Presenter.
	 */
	void setPresenter(IFormPresenter<T> presenter);


	/**
	 * Vaidiert das Formualr.
	 * @return true wenn alle felder korrekt ausgefuellt sind.
	 */
	boolean validate();
}
