package de.swm.commons.mobile.client.event;

import com.google.gwt.event.shared.HandlerRegistration;

public interface HasIconClickHandlers {
	  HandlerRegistration addIconClickHandler(IconClickHandler handler);
}
