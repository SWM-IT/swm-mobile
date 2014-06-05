package de.swm.gwt.client.eventbus;

import de.swm.gwt.client.interfaces.ILocation;
import de.swm.gwt.client.mobile.IPage;



/**
 * Event Handlder interface fuer mobile Plattformen.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2009-2011, SWM Services GmbH
 * 
 */
public interface IMobileEventHandler {

	/**
	 * Wird aufgerufen, wenn ein event ausgeloest wurde. Wird optional von <code>handleEvent</code> aufgerufen, wenn im
	 * event eine target {@link ILocation} mitgegeben wurde.
	 * 
	 * @param eventType
	 *            der event typ.
	 * 
	 * @param originator
	 *            Die Seite (mobile Plattformen sind Seitenbasiert) die das Event ausgeloest hat.
	 * 
	 * 
	 * @param customData
	 *            customData daten um ein Formular/Liste zu cutumizen (anzupassen) mit neuen Titel, Untertitle etc..)
	 */
	void handleEvent(IEvent eventType, IPage originator, IMobileCustomData customData);

}
