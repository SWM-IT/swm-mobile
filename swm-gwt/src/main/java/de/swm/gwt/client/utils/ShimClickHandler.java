package de.swm.gwt.client.utils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import java.util.ArrayList;
import java.util.List;


/**
 * Ermoglichet einer Komponente die einen Clickehandler benoetigt weitere ClickHanlder zur laufzeit hinzuzufuegen.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 */
public class ShimClickHandler implements ClickHandler, HasClickHandlers {

	private final List<ClickHandler> clickHandlers = new ArrayList<ClickHandler>();
	private boolean isEnabled = true;


	@Override
	public void onClick(ClickEvent event) {
		if (isEnabled) {
			for (ClickHandler clickHandler : clickHandlers) {
				clickHandler.onClick(event);
			}
		}

	}

	/**
	 * Enabled / disabled the shim click hanlder.
	 *
	 * @param isEnabled true is enabled.
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * Removes all click Hanlders.
	 */
	public void removeAll() {
		clickHandlers.clear();
	}


	@Override
	public void fireEvent(GwtEvent<?> event) {
		throw new IllegalArgumentException("Not supported");

	}


	@Override
	public HandlerRegistration addClickHandler(final ClickHandler handler) {
		clickHandlers.add(handler);
		return new HandlerRegistration() {

			@Override
			public void removeHandler() {
				clickHandlers.remove(handler);

			}
		};
	}

}
