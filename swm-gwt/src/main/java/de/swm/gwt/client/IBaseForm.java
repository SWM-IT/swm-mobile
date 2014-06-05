package de.swm.gwt.client;

import com.google.gwt.user.client.ui.Widget;

import de.swm.gwt.client.eventbus.ICustomData;
import de.swm.gwt.client.interfaces.ILocation;

/**
 * Allgemeines Interface fuer Formulare (gemeinsame Methoden editierbare und
 * disabled-Formulare.
 *
 * copyright (C) 20xx, SWM Services GmbH S-IP-AN-EE
 * @param <T> Das DTO des Formulars.
 */
public interface IBaseForm<T> {

	/**
	 * Rendert das Formular in die uebergebene Location.
	 * @param currentLocation  die aktelle location
	 * @param customData date fuer anpassungen.
	 */
	void render(ILocation currentLocation, ICustomData customData);
	
	
	/**
	 * Entfernt das Formular aus der Location.
	 * 
	 * @param location Location, aus der das Formular entfernt werden soll
	 */
	public void remove(ILocation location);

	/**
	 * Liefert das Widget welches das Formular darstellt.
	 * @return das widget.
	 */
	Widget getWidget();


	/**
	 * Zeigt ein DTO im Formular an.
	 * @param toDisplay das DTO.
	 */
	void displayDTO(T toDisplay);

	/**
	 * Liefert das aktuell angezeigte DTO zurueck.
	 * @return das DTO.
	 */
	T getDisplayedDTO();


}
