package de.swm.gwt.client.asyncjs;

import com.google.gwt.event.dom.client.ClickEvent;



/**
 * Ein Forward event IF welches al Paramener einen ClickHanlder nimmt.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public interface IForwardEventWithClickEvent {

	/**
	 * Fuehrt das forward event aus.
	 * 
	 * @param clickEvent
	 *            click Event.
	 */
	void execute(ClickEvent clickEvent);

}
