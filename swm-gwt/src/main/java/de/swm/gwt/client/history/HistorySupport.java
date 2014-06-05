package de.swm.gwt.client.history;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import de.swm.gwt.client.eventbus.IDispatcher;
import de.swm.gwt.client.eventbus.IEvent;



/**
 * Support fuer event-Forwarding ueber History-Tokens.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 * @param <T>
 *            Haupt-Event Klasse
 * @param <Z>
 *            eine optionale Sub-Event Klasse
 * 
 */
public class HistorySupport<T extends Enum<T> & IEvent, Z extends Enum<Z> & IEvent> implements
		ValueChangeHandler<String> {

	private final HistoryTokenParser<T, Z> parser;



	/**
	 * Default constructor.
	 * 
	 * @param dispacher
	 *            der anwendungsweite dispatcher
	 * @param plattformEventClass
	 *            der Haupt-Event Klasse
	 * @param optionalSubEventClass
	 *            eine optionale Sub-Event Klasse.
	 */
	public HistorySupport(IDispatcher dispacher, Class<T> plattformEventClass, Class<Z> optionalSubEventClass) {
		this.parser = new HistoryTokenParser<T, Z>(dispacher, plattformEventClass, optionalSubEventClass);
	}



	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		final String historyValue = event.getValue();
		handleHistoryEvent(historyValue);

	}



	/**
	 * History event mit dem Toen als String.
	 * 
	 * @param historyValue
	 *            der history token
	 */
	public void handleHistoryEvent(final String historyValue) {
		if (historyValue != null) {
			GWT.log("History-Change-Event: " + historyValue);
			this.parser.parseTokenAndFireEvents(historyValue);
		}
	}
}
