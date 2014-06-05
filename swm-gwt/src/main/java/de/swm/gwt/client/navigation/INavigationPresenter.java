package de.swm.gwt.client.navigation;

import de.swm.gwt.client.navigation.INavigationContent;



/**
 * Stellt einen Navigationsbereich dar der ebetweder direkt von einem Persentern oder von mehreren Presentern mit einem
 * vorgeschlateten dispatch implementiert werden kann.
 * 
 * @author wiese.daniel copyright (C) 2011, SWM Services GmbH
 * 
 */
public interface INavigationPresenter {

	/**
	 * Fuegt eine Untermenue zum Menue hinzu.
	 * 
	 * @param toAdd
	 *            das hinzuzufuegende element.
	 */
	void addSubMenuContent(INavigationContent toAdd);

}