package de.swm.gwt.client.exception;

import com.google.gwt.core.client.GWT;



/**
 * Hilfsklasse, die auf andere Seiten weiterleiten oder die aktuelle Seite neuladen kann.
 * 
 * 
 * @author Steiner.Christian <br>
 *         copyright (C) 2011, SWM Services GmbH S-IP-AN-EE
 */
public class ApplicationRedirecter {

	private static final String HOSTED_MODE_SUFFIX = "?gwt.codesvr=#:9997";



	/**
	 * Default constructor.
	 */
	private ApplicationRedirecter() {

	}



	/**
	 * Fuehrt ein Redirect zur Base-URL durch. Das heisst, es wird auf die Basis-URL der GWT-Application weitergeleitet.
	 */
	public static void redirectToBase() {
		final StringBuilder url = new StringBuilder();
		url.append(GWT.getHostPageBaseURL());
		if (!GWT.isScript()) {
			url.append(HOSTED_MODE_SUFFIX.replace("#", getIP(GWT.getHostPageBaseURL())));
		}
		redirect(url.toString());

	}



	/**
	 * Leitet auf eine andere Webseite mit Hilfe von JavaScript-Mitteln weiter.
	 * 
	 * @param url
	 *            die Ziel-URL, auf die weitergeleitet werden soll.
	 */
	public static native void redirect(String url)
	/*-{
	 $wnd.location.replace(url);
	 }-*/;



	/**
	 * Laedt die Anwendung mit Hilfe von JavaScript-Mitteln neu.
	 */
	public static native void reloadApp()
	/*-{
	$wnd.location.reload();
	}-*/;



	/**
	 * Extrahiert die IP/DNS aus der Module base URL.
	 * 
	 * @param moduleBaseUrl
	 *            die base url.
	 * @return die IP oder DNS-Name
	 */
	private static String getIP(String moduleBaseUrl) {
		String[] split = moduleBaseUrl.split(":");
		if (split.length > 1) {
			return split[1].substring("//".length());
		}

		return null;
	}
}
