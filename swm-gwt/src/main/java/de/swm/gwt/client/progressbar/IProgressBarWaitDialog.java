package de.swm.gwt.client.progressbar;

import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;



/**
 * Blendet einen Wartedialog an.
 * 
 * @author Wiese.Daniel <br>
 *         copyright (C) 2010 - 2011, SWM Services GmbH
 * 
 */
public interface IProgressBarWaitDialog {

	/**
	 * Startet den Progress-Bar dialog und wrapped einen Callback.
	 * 
	 * @param <T>
	 *            der Typ das callbacks
	 * @param callbackToExecute
	 *            der callback der ausgefuehrt wird und eventuell zurueckgerollt wird.
	 * @return der callback
	 */
	<T> AsyncCallback<T> start(AsyncCallback<T> callbackToExecute);
	
	/**
	 * Startet die Progress-Bar um code splitting zu realisieren.
	 * @param callbackToExecute der asynchrone callback.
	 */
	void start(final RunAsyncCallback callbackToExecute);

}