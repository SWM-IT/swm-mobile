package de.swm.gwt.client.utils;

import java.util.logging.Logger;

/**
 * Utility class.
 *
 * @author wiese.daniel
 *         <br>copyright (C) 2011, SWM Services GmbH
 */
public final class Utils {

	private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());


	/**
	 * Util constructor.
	 */
	private Utils() {
	}

	/**
	 * Sreibt eine Debug meldung in die Browser-Console.
	 *
	 * @param msg die Nachricht die geschriben werden soll
	 */
	public static void console(String msg) {
		LOGGER.info(msg);
	}

}
