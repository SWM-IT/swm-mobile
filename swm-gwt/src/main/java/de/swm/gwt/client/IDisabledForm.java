package de.swm.gwt.client;


/**
 * Allgemeines Interface fuer disabled GWT-Formulare.
 *
 * copyright (C) 20xx, SWM Services GmbH S-IP-AN-EE
 * @param <T> Das DTO des Formulars.
 */
public interface IDisabledForm<T> extends IBaseForm<T> {

	/**
	 * Setzt den Presenter des Formulars.
	 * @param presenter  der Presenter.
	 */
	void setPresenter(IDisabledFormPresenter<T> presenter);

}
