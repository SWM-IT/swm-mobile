package de.swm.gwt.client.mobile.network;

import de.swm.gwt.client.mobile.network.rpcservice.NetworkServiceAsync;

/**
 * Kontrolliert das Vorhandensein einer Netzwerkverkverbindung.
 * @author wiese.daniel
 * <br>copyright (C) 2011, SWM Services GmbH
 *
 */
public interface INetworkMonitor {

	/**
	 * Starts the network monitoring service.
	 */
	void startService();
	
	/**
	 * Stops the network monitoring service.
	 */
	void stopService();

	/**
	 * Liefert den Service endpoint des Monitoring services.
	 * @return den service endpoint des monitoring services.
	 */
	NetworkServiceAsync getServiceEndpoint();

	/**
	 * Starts the service with an default state .
	 *
	 * @param defaultState default state
	 */
	void startService(NetworkEvents defaultState);

	/**
	 * Sets the native network monito > if o network no events wil befired.
	 * @param nativeNetworkHandler  native network handler
	 */
	void setNativeHandler(NativeNetworkHandler nativeNetworkHandler);

	/**
	 * Will set the network polling frequence in ms.
	 * @param pollingFrequenceInMs polling frequence in ms
	 */
	void setNetworkCheckInterval(int pollingFrequenceInMs);

	/**
	 * Will set the delay before the first network check in ms.
	 * @param delayInMs before first network check
	 */
	void setNetworkCheckDelay(int delayInMs);

	/**
	 * Will set the interval if the network is not avaliable. If 0 the interval is eqal to the default check intervall.
	 * @param networkMonCheckIntervalUnavailableInMs     he interval if the network is not avaliable
	 */
	void setNetworkCheckIntervalWhenNotAvailable(int networkMonCheckIntervalUnavailableInMs);
}