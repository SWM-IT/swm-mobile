package de.swm.gwt.client.mobile.network.rpcservice;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchrones Interface von {@link NetworkService}.
 * @author wiese.daniel
 * <br>copyright (C) 2011, SWM Services GmbH
 *
 */
public interface NetworkServiceAsync {
	
	/**
	 * Liert true wenn der server und die Verbindng erreichbar ist.
	 * @param callback true wenn errreichbar.
	 */
	void checkNetwork(AsyncCallback<String> callback);

}
