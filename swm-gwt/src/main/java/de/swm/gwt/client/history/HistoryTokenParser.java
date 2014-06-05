package de.swm.gwt.client.history;

import de.swm.gwt.client.eventbus.CustomData;
import de.swm.gwt.client.eventbus.ICustomData;
import de.swm.gwt.client.eventbus.IDispatcher;
import de.swm.gwt.client.eventbus.IEvent;
import de.swm.gwt.client.eventbus.IForwardEvent;
import de.swm.gwt.client.interfaces.ILocation;



/**
 * Parsed den ISS History-Token. Der Token ist folgender massen definiert. url#EVENT1=Value1;NEXT_EVENT_2=Value2.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * @param <T>
 *            Haupt-Event Klasse
 * @param <Z>
 *            eine optionale Sub-Event Klasse
 * 
 */
public class HistoryTokenParser<T extends Enum<T> & IEvent, Z extends Enum<Z> & IEvent> {

	private final IDispatcher dispacher;
	private final Class<T> plattformEventClass;
	private final Class<Z> optionalSubEventClass;



	/**
	 * Default constructor.
	 * 
	 * @param dispacher
	 *            der dispatcher
	 * @param plattformEventClass
	 *            der Haupt-Event Klasse
	 * @param optionalSubEventClass
	 *            eine optionale Sub-Event Klasse.
	 * 
	 * 
	 */
	public HistoryTokenParser(IDispatcher dispacher, Class<T> plattformEventClass, Class<Z> optionalSubEventClass) {
		this.dispacher = dispacher;

		this.plattformEventClass = plattformEventClass;
		this.optionalSubEventClass = optionalSubEventClass;
	}



	/**
	 * Parsed das token und feuert ein event.
	 * 
	 * @param token
	 *            das token
	 * @return das custom data objekt, welches dem event bus gesentet wurde.
	 */
	public ICustomData parseTokenAndFireEvents(String token) {
		final String[] split = token.split(";");
		if (split.length > 1) {
			// embedded event found
			final IEvent firstEventToFire = parseEventType(split[0]);
			final IEvent secondEventToFire = parseEventType(split[1]);
			IForwardEvent forwardEvent = null;
			// falls forward event existiert, dieses verpacken
			if (secondEventToFire != null) {
				final ICustomData custDataEvent2 = parseUserData(split[1]);
				forwardEvent = new IForwardEvent() {
					@Override
					public void execute() {
						dispacher.fireEvent(secondEventToFire, (ILocation) null, custDataEvent2);
					}



					/**
					 * Die To-String-Methode dient zu Serialisieren des Forward-Events (NICHT ANEDERN!)
					 */
					@Override
					public String toString() {
						if (custDataEvent2 != null && custDataEvent2.userObject() != null) {
							return secondEventToFire.eventName() + "=" + custDataEvent2.userObject();
						} else {
							return secondEventToFire.eventName();
						}
					}

				};
			}

			if (firstEventToFire != null && forwardEvent != null) {
				final ICustomData userData = parseUserData(split[0], forwardEvent);
				dispacher.fireEvent(firstEventToFire, (ILocation) null, userData);
				return userData;
			} else if (firstEventToFire != null) {
				final ICustomData userData = parseUserData(split[0]);
				dispacher.fireEvent(firstEventToFire, (ILocation) null, userData);
				return userData;
			}
		} else {
			IEvent eventToFire = parseEventType(token);
			if (eventToFire != null) {
				final ICustomData userData = parseUserData(token);
				dispacher.fireEvent(eventToFire, (ILocation) null, userData);
				return userData;
			}
		}

		return null;
	}



	/**
	 * Extrahier unser aramer die zum jeweiligen Event gehoeren.
	 * 
	 * @param eventPart
	 *            der event als string
	 * @return user parameter oder null wenn keine.
	 */
	private ICustomData parseUserData(String eventPart) {
		final String[] split = eventPart.split("=");
		if (split != null && split.length > 1) {
			final ICustomData userDataContainer = new CustomData(null, null, null);
			userDataContainer.setUserObject(split[1]);
			return userDataContainer;

		}

		return null;
	}



	/**
	 * Extrahier unser aramer die zum jeweiligen Event gehoeren.
	 * 
	 * @param eventPart
	 *            der event als string
	 * @param forwardEvent
	 *            der forward event
	 * @return user parameter oder null wenn keine.
	 */
	private ICustomData parseUserData(String eventPart, IForwardEvent forwardEvent) {
		final CustomData customData = new CustomData(null, null, null);
		customData.setForwardEvent(forwardEvent);
		final String[] split = eventPart.split("=");
		if (split != null && split.length > 1) {
			customData.setUserObject(split[1]);

		}

		return customData;
	}



	/**
	 * Parsed das event das als Token uebergeben wurde.
	 * 
	 * @param eventPart
	 *            der event FormEvent / PlatformEvent
	 * @return null wenn nicht fefunden
	 */
	private IEvent parseEventType(String eventPart) {
		final String[] split = eventPart.split("=");
		try {
			return Enum.valueOf(plattformEventClass, split[0]);
		} catch (Exception ex) {

		}
		if (this.optionalSubEventClass != null) {
			try {
				return Enum.valueOf(optionalSubEventClass, split[0]);
			} catch (Exception ex) {

			}
		}

		return null;
	}

}
