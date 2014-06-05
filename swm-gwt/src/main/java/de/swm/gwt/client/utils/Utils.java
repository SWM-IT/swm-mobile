package de.swm.gwt.client.utils;

/**
 * Utility class.
 * @author wiese.daniel
 * <br>copyright (C) 2011, SWM Services GmbH
 *
 */
public final class Utils {
	
	/**
	 * Util constructor.
	 */
	private Utils() {
	}
	
	/**
	 * Sreibt eine Debug meldung in die Browser-Console.
	 * @param msg die Nachricht die geschriben werden soll
	 */
	public static void console(String msg) {
		consoleLogNative("[UI] " + msg);
	}
	

	/**
	 * Writes a log message to widow console.
	 * @param msg the message
	 */
	private static native void consoleLogNative(String msg) /*-{
		var log = $doc.getElementById('log');
		if (log) {
			log.innerHTML = msg + '<br/>' + log.innerHTML;
		}
		else {
          if ($wnd.console) {
              $wnd.console.log(msg);
          }
		}			
	}-*/;

}
