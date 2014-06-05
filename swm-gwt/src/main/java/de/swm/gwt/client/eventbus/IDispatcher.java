package de.swm.gwt.client.eventbus;

import java.util.Set;

import de.swm.gwt.client.interfaces.ILocation;
import de.swm.gwt.client.mobile.IPage;



/**
 * Dispatcher fuer verschiedene event-types. Ein Dispatcher dient zum Verarbeiten von events eines bestimmten Enum-Typs.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2009, SWM Services GmbH
 * 
 */
public interface IDispatcher {

	/**
	 * Registriert einen Event-Type und einen Controller am Dispatcher.
	 * 
	 * @param event
	 *            der event-typ
	 * @param handler
	 *            der handler (der controller)
	 */
	void registerEventTypes(IEvent event, IEventHandler handler);



	/**
	 * Registriert einen Event-Type und einen Controller am Dispatcher (fuer mobile Plattformen).
	 * 
	 * @param event
	 *            der event-typ
	 * @param handler
	 *            der handler (der controller)
	 */
	void registerEventTypes(IEvent event, IMobileEventHandler handler);



	/**
	 * Loest ein Event aus und benachrichtigt die registrierten Controller.
	 * 
	 * @param event
	 *            der event
	 */
	void fireEvent(IEvent event);



	/**
	 * Loest ein Event aus und benachrichtigt die registrierten Controller, weiterhin wird ein forward-event en den
	 * Presenter uebergeben der dieses weiterleiten kann.
	 * 
	 * @param event
	 *            der event
	 * @param eventToForward
	 *            das event welches weitergeleitet werden kann
	 */
	void fireEvent(IEvent event, IForwardEvent eventToForward);



	/**
	 * Loest ein Event aus und benachrichtigt die registrierten Controller, weiterhin wird ein forward-event en den
	 * Presenter uebergeben der dieses weiterleiten kann.
	 * 
	 * @param event
	 *            der event
	 * @param eventToForward
	 *            das event welches weitergeleitet werden kann
	 * @param location
	 *            uebergibt man zusaetzlich die Location, also den Bereich wohin der Presenter seine View hinein rendern
	 *            soll.
	 */
	void fireEvent(IEvent event, IForwardEvent eventToForward, ILocation location);



	/**
	 * Loest ein Event aus und benachrichtigt die registrierten Controller, weiterhin wird ein forward-event en den
	 * Presenter uebergeben der dieses weiterleiten kann.
	 * 
	 * @param event
	 *            der event
	 * @param eventToForward
	 *            das event welches weitergeleitet werden kann
	 * @param location
	 *            uebergibt man zusaetzlich die Location, also den Bereich wohin der Presenter seine View hinein rendern
	 *            soll.
	 * @param customData
	 *            daten um ein Formular/Liste zu cutumizen (anzupassen) mit neuen Titel, Untertitel etc..)
	 * 
	 */
	void fireEvent(IEvent event, IForwardEvent eventToForward, ILocation location, ICustomData customData);



	/**
	 * Loest ein Event aus und benachrichtigt die registrierten Presenter.
	 * 
	 * @param event
	 *            der event
	 * @param location
	 *            uebergibt man zusaetzlich die Location, also den Bereich wohin der Presenter seine View hinein rendern
	 *            soll.
	 */
	void fireEvent(IEvent event, ILocation location);



	/**
	 * Loest ein Event aus und benachrichtigt die registrierten Presenter.
	 * 
	 * @param event
	 *            der event
	 * @param customData
	 *            daten um ein Formular/Liste zu cutumizen (anzupassen) mit neuen Titel, Untertitel etc..)
	 */
	void fireEvent(IEvent event, ICustomData customData);



	/**
	 * Loest ein Event aus und benachrichtigt die registrierten Presenter.
	 * 
	 * @param event
	 *            der event
	 * @param location
	 *            uebergibt man zusaetzlich die Location, also den Bereich wohin der Presenter seine View hinein rendern
	 *            soll.
	 * @param customData
	 *            daten um ein Formular/Liste zu cutumizen (anzupassen) mit neuen Titel, Untertitel etc..)
	 */
	void fireEvent(IEvent event, ILocation location, ICustomData customData);



	// /Mobiler Teil
	/**
	 * Loest ein Event aus und benachrichtigt die registrierten Controller.
	 * 
	 * @param event
	 *            der event
	 */
	void fireMobileEvent(IEvent event);



	/**
	 * Loest ein Event aus und benachrichtigt die registrierten Controller.
	 * 
	 * @param event
	 *            der event
	 * @param originatorPage
	 *            Die seite (mobile Plattformen sind Seitenbasiert) die das Event ausgeloest hat.
	 */
	void fireMobileEvent(IEvent event, IPage originatorPage);



	/**
	 * Loest ein Event aus und benachrichtigt die registrierten Controller.
	 * 
	 * @param event
	 *            der event
	 * @param originatorPage
	 *            Die seite (mobile Plattformen sind Seitenbasiert) die das Event ausgeloest hat.
	 * @param customData
	 *            die benutzerdaten oder null die mit dem Event mitgesendet werden.
	 */
	void fireMobileEvent(IEvent event, IPage originatorPage, IMobileCustomData customData);



	/**
	 * Disabled ein bestimmtes Event(s) auf dem Event-Bus. Diese werden dann nicht mehr getriggert.
	 * 
	 * @param eventToDisable
	 *            das event welches disabled werden soll.
	 */
	void disableEvents(IEvent... eventToDisable);



	/**
	 * Disabled alle aktuell registrierten Events auf dem EventBus und liefert diese zurueck.
	 * 
	 * @return alle events auf dem EventBus
	 */
	Set<IEvent> disableAllEvents();



	/**
	 * Liefert alle aktuell disableten Events.
	 * 
	 * @return alle aktuell disableten events.
	 */
	Set<IEvent> getAllDisabledEvents();



	/**
	 * Enabled alle aktuell disableten Events.
	 */
	void enableAllEvents();



	/**
	 * Evebled eine Menge von Events auf dem Event-Bus.
	 * 
	 * @param eventToEnable
	 *            das event welches enebled werden soll.
	 */
	void enableEvents(IEvent... eventToEnable);
}