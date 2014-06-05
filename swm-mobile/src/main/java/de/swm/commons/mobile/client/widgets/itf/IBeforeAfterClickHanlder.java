package de.swm.commons.mobile.client.widgets.itf;

import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Click handler, which reacts to before and after click events.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2013, Stadtwerke München GmbH
 */
public interface IBeforeAfterClickHanlder {

	/**
	 * Called when a native click event is fired.
	 *
	 * @param event the {@link com.google.gwt.event.dom.client.ClickEvent} that was fired
	 */
	void beforeClick(ClickEvent event);

	/**
	 * Called when a native click event is fired.
	 *
	 * @param event the {@link com.google.gwt.event.dom.client.ClickEvent} that was fired
	 */
	void afterClick(ClickEvent event);
}
