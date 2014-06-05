package de.swm.gwt.client.interfaces;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;



/**
 * Repesentiert eine Position am Bildschirm. Dieses kann ein Pannel {@link ContentPanelLocation} innerhalb eines Layouts
 * sein oder ein Div-Tag auf eine HTML Seite {@link HtmlDivTagLocation}.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2010, SWM Services GmbH
 * 
 */
public interface ILocation {

	/**
	 * Liefert die Widgets, die sich innerhalb der Location befinden.
	 * 
	 * @return dei widgents
	 */
	List<Widget> getItems();



	/**
	 * Fuegt ein Widget zu der Loacation hinzu z.B. ein {@link IForm}
	 * 
	 * @param toAdd
	 *            das Widget welches hinzugefuegt werden soll
	 */
	void add(Widget toAdd);



	/**
	 * Fuegt ein Widget zu der Loacation hinzu die als Letztes verwendet wurde relavent bei z.B. einer
	 * {@link TabPanelLocation}
	 * 
	 * @param toAdd
	 *            das Widget welches hinzugefuegt werden soll
	 */
	void addToLastLocation(Widget toAdd);



	/**
	 * Entfernt ein Widget.
	 * 
	 * @param toRemove
	 *            das zu entfernende widget
	 */
	void remove(Widget toRemove);



	/**
	 * Entfernt alle Widgets aus der angegebenen Location.
	 */
	void removeAll();



	/**
	 * Ueberprueft ob ein Widget bereits in der Location enthalten ist.
	 * 
	 * @param toCheck
	 *            das zu ueberpruefende Widget
	 * @return true wenn enthalten
	 */
	boolean contains(Widget toCheck);



	/**
	 * Redert das Widget sammt den Unterwidgets auf dem Bildschirm.
	 */
	void render();



	/**
	 * Stellt die {@link ILocation} als GWT Widget dar.
	 * 
	 * @return die Location als Widget
	 */
	Widget asWidget();

}
