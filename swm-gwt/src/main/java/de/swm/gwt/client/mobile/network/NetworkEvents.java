package de.swm.gwt.client.mobile.network;

import de.swm.gwt.client.eventbus.IEvent;



/**
 * Definiert die Events die das Vorhandensein einer Netzwerkverbindung ueberpruefen.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public enum NetworkEvents implements IEvent {

	/**
	 * Wird gefuert sobald das Netzwerk nicht mehr verfuegbar ist.
	 */
	NETWORK_AVAILABLE("NETWORK_AVAILABLE"),

	/**
	 * Wird gefuert sobald das Netzwekr wieder verfuegbar ist.
	 */
	NETWORK_UNAVAILABLE("NETWORK_UNAVAILABLE");

	private final String eventName;



	/**
	 * Default constructor.
	 * 
	 * @param eventName
	 *            das event.
	 */
	private NetworkEvents(String eventName) {
		this.eventName = eventName;
	}



	@Override
	public String eventName() {
		return eventName;
	}

}
