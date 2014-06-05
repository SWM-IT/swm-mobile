package de.swm.gwt.client;

import de.swm.gwt.client.interfaces.ILocation;

/**
 * Allgemeines Interface fuer GWT-Listen.
 *
 * @author Steiner.Christian <br>
 *         copyright (C) 13, SWM Services GmbH S-IP-AN-EE
 *         @param <T> Das DTO des Formulars.
 */
public interface IList<T> {

	/**
	 * Setzt den Presenter der Liste.
	 * @param presenter  der Presenter.
	 */
	void setPresenter(IListPresenter<T> presenter);

	
	/**
	 * Rendert die Liste in die uebergebene Location.
	 * @param currentLocation  die aktelle location
	 */
	void render(ILocation currentLocation);
	
	
	/**
	 * Entfernt die Liste aus der Location.
	 * 
	 * @param location Location, aus der die Liste entfernt werden soll
	 */
	public void remove(ILocation location);
	
	/**
	 * reloads the list data.
	 */
	void onLoad();
	
	
	/**
	 * selects a row in the datagrid.
	 *
	 * @param toSelect the object/row to select
	 */
	void selectRow(T toSelect);

}
