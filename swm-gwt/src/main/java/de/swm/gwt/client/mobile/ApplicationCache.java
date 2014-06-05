package de.swm.gwt.client.mobile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;



/**
 * Hilfsklasse um Zugriff auf den HTML5 Cache zu bekommen.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public final class ApplicationCache extends JavaScriptObject {

	// getStatus() values:
	/** Cache status code. **/
	public static final short UNCACHED = 0;
	/** Cache status code. **/
	public static final short IDLE = 1;
	/** Cache status code. **/
	public static final short CHECKING = 2;
	/** Cache status code. **/
	public static final short DOWNLOADING = 3;
	/** Cache status code. **/
	public static final short UPDATEREADY = 4;
	/** Cache status code. **/
	public static final short OBSOLETE = 5;

	// event types for addEventListener():
	/** Cache status code. **/
	public static final String ONCHECKING = "checking";
	/** Cache status code. **/
	public static final String ONERROR = "error";
	/** Cache status code. **/
	public static final String ONNOUPDATE = "noupdate";
	/** Cache status code. **/
	public static final String ONDOWNLOADING = "downloading";
	/** Cache status code. **/
	public static final String ONPROGRESS = "progress";
	/** Cache status code. **/
	public static final String ONUPDATEREADY = "updateready";
	/** Cache status code. **/
	public static final String ONCACHED = "cached";



	/**
	 * Default constructor.
	 */
	protected ApplicationCache() {
	}



	/**
	 * Returns <code>true</code> if the Application Cache API is supported on the running platform.
	 * 
	 * @return true if supproted
	 */
	public static native boolean isSupported() /*-{
												return typeof $wnd.applicationCache != "undefined";
												}-*/;



	/**
	 * Liefert den cache.
	 * 
	 * @return der cache.
	 */
	public static native ApplicationCache getApplicationCache() /*-{
																return $wnd.applicationCache;
																}-*/;



	/**
	 * Handler fuer die verscheidenen cache events.
	 * 
	 * @param listener
	 *            der listener
	 * @param event
	 *            das event
	 */
	private static void handleCacheEvents(EventListener listener, Event event) {
		UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
		if (ueh != null) {
			try {
				listener.onBrowserEvent(event);
			} catch (Throwable t) {
				ueh.onUncaughtException(t);
			}
		} else {
			listener.onBrowserEvent(event);
		}
	}



	/**
	 * Fugt einen event listener hinzu.
	 * 
	 * @param type
	 *            der typ (wleche events)
	 * @param listener
	 *            der listener
	 * @param useCapture
	 *            true wenn der flag verwendet wirden soll.
	 */
	public native void addEventListener(String type, EventListener listener, boolean useCapture) /*-{
																									this.addEventListener(
																									type,
																									function(event) {
																									@de.swm.gwt.client.mobile.ApplicationCache::handleCacheEvents(Lcom/google/gwt/user/client/EventListener;Lcom/google/gwt/user/client/Event;) (listener, event);
																									},
																									useCapture
																									);
																									}-*/;



	/**
	 * Cache operation, siehe spec.
	 */
	public native void update() /*-{
								this.update();
								}-*/;



	/**
	 * Cache operation, siehe spec.
	 */
	public native void swapCache() /*-{
									this.swapCache();
									}-*/;



	/**
	 * Cache operation, siehe spec.
	 * 
	 * @return den status code
	 */
	public native int getStatus() /*-{
									return this.status;
									}-*/;



	/**
	 * Cache operation, siehe spec.
	 * 
	 * @return wenn online
	 */
	public native boolean isOnline() /*-{
										return $wnd.navigator.onLine;
										}-*/;
}
