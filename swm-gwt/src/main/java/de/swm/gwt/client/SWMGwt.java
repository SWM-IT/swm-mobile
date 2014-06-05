package de.swm.gwt.client;

import de.swm.gwt.client.theme.GWTTheme;

import java.util.logging.Logger;

/**
 * Base class of the swm-gwt library.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2014, Stadtwerke München GmbH
 */
public class SWMGwt {

	private static final Logger LOGGER = Logger.getLogger(SWMGwt.class.getName());

	private static GWTTheme theme;

	/**
	 * Return the current theme of the Application. By default, this will be the base theme.
	 *
	 * @return the current theme.
	 */
	public static GWTTheme getTheme() {
		if (theme == null) {
			LOGGER.info("No Theme ist set");
		}
		return theme;
	}


	/**
	 * Sets the current theme of the Application.
	 *
	 * @param toSet the theme to set.
	 */
	public static void setTheme(GWTTheme toSet) {
		theme = toSet;
	}

}
