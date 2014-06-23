package de.swm.gwt.client.responsive;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author Ed Bras
 */
public interface IHasMediaMatchChangeHandlers {
	HandlerRegistration addMatchMediaChangeHandler(IMatchMediaChangeHandler handler);
}
