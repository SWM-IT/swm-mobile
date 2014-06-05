package de.swm.gwt.client.eventbus;

/**
 * Definiert einen Event der zum Event-Bus gesendet werden kann.
 * @author Wiese.Daniel
 * <br>copyright (C) 2011, SWM Services GmbH
 *
 */
public interface IEvent {
	
	/**
	 * Der Name des Events.
	 * @return der Name.
	 */
	String eventName();

}
