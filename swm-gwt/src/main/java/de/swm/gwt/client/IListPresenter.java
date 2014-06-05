package de.swm.gwt.client;


/**
 * Allgemeines Interface fuer einen Listen-Presenter.
 *
 * @author Steiner.Christian
 * <br>copyright (C) 13, SWM Services GmbH S-IP-AN-EE
 * @param <T> der Typ des Daten Transfer Objektes
 */
public interface IListPresenter<T> {

	/**
	 * Wenn eine Zeile der Liste gewaehlt wurde.
	 * @param dto  das DTO.
	 */
	void onRowSelected(T dto);

	/**
	 * Wenn der Button 'Neu' geklickt wurde.
	 */
	void onAddClicked();


}
