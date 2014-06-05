package de.swm.gwt.client.eventbus;

import de.swm.gwt.client.interfaces.ILocation;



/**
 * Event Handlder interface.
 * 
 * @author wiese.daniel
 * <br>copyright (C) 2009-2010, SWM Services GmbH
 * 
 */
public interface IEventHandler {

	/**
	 * Wird aufgerufen, wenn ein event ausgeloest wurde. Wird optional von <code>handleEvent</code> aufgerufen, wenn im
	 * event eine target {@link ILocation} mitgegeben wurde.
	 * 
	 * @param eventType
	 *            der event typ.
	 * @param location
	 *            die location in die sich der View des Presenters hineinrendern soll.
	 * @param customData
	 *            customData daten um ein Formular/Liste zu cutumizen (anzupassen) mit neuen Titel, Untertitle etc..)
	 */
	void handleEvent(IEvent eventType, ILocation location, ICustomData customData);

}
